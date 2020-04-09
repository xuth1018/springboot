package com.company.project.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.SimpleFormatter;

@Component
public class RedisUtil {

    private static Logger logger = LoggerFactory.getLogger(RedisUtil.class);

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    //============================Common=============================

    /**
     * 设置缓存时间
     * @param key
     * @param time
     * @return
     */
    public boolean expire(String key,long time){
        try{
            if(time>0){
                redisTemplate.expire(key,time,TimeUnit.SECONDS);
            }
            return true;
        }catch (Exception e){
            logger.info("===RedisUtil.expire()===error:{}",e);
            return  false;
        }
    }

    /**
     * 根据key获取过期时间
     * @param key
     * @return 0 代表永久有效
     */
    public long getExpire(String key){
        return redisTemplate.getExpire(key,TimeUnit.SECONDS);
    }

    /**
     * 判断是否存在key
     * @param key
     * @return
     */
    public boolean hasKey(String key){
        try{
            return redisTemplate.hasKey(key);
        }catch (Exception e){
            logger.info("===RedisUtil.hasKey()===error:{}",e);
            return false;
        }
    }

    /**
     * 删除缓存
     * @param key 可以传一个值 或者多个值
     */
    public void del(String... key){
        if(null!=key && key.length>0){
            if(key.length==1){
                redisTemplate.delete(key[0]);
            }else{
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }

    //=============================String============================
    /**
     * 普通键值对获取
     * @param key
     * @return
     */
    public Object get(String key){
        return null==key?null:redisTemplate.opsForValue().get(key);
    }

    /**
     * 普通缓存放入
     * @param key
     * @param value
     * @return
     */
    public boolean set(String key,Object value){
        return set(key,value,-1L);
    }

    /**
     * 普通缓存放入并设置时间
     * @param key
     * @param value
     * @param time
     * @return
     */
    public boolean set(String key,Object value,long time){
        try {
            if(time>0){
                redisTemplate.opsForValue().set(key,value,time, TimeUnit.SECONDS);
            }else{
                redisTemplate.opsForValue().set(key,value);
            }
            return true;
        }catch (Exception e){
            logger.info("===RedisUtil.set()===error:{}",e);
            return false;
        }
    }

    /**
     * 递增
     * @param key
     * @param delta
     * @return
     */
    public long incr(String key,long delta){
        if(delta<=0){
            throw new RuntimeException("递增因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key,delta);
    }

    /**
     * 递减
     * @param key 键
     * @param delta 要减少几(小于0)
     * @return
     */
    public long decr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递减因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, -delta);
    }

    //=======================Map==================================

    /**
     * HashGet 获取map中指定key的值
     * @param key 不能为null
     * @param item 不能为null
     * @return
     */
    public Object hget(String key,String item){
        return redisTemplate.opsForHash().get(key,item);
    }

    /**
     * 获取hashkey对应的所有值
     * @param key
     * @return
     */
    public Map<Object,Object> hmget(String key){
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * hashset 存入hashmap
     * @param key
     * @param map
     * @return
     */
    public <T> boolean hmset(String key, Map<String,T> map){
        return hmset(key,map,-1);
    }

    /**
     * hashset 存入hashmap并设置缓存时间
     * @param key
     * @param map
     * @param time
     * @return
     */
    public <T> boolean hmset(String key, Map<String,T> map, long time){
        try{
            redisTemplate.opsForHash().putAll(key,map);
            if(time>0){
                expire(key,time);
            }
            return true;
        }catch (Exception e){
            logger.info("===RedisUtil.hmset()===error:{}",e);
            return false;
        }
    }



    /**
     * 像一张hash表中存入数据，如果不存在就创建
     * @param key
     * @param item
     * @param value
     * @return
     */
    public boolean hset(String key,String item,Object value){
        try {
            redisTemplate.opsForHash().put(key,item,value);
            return true;
        }catch (Exception e){
            logger.info("===RedisUtil.hset()===error:{}",e);
            return false;
        }
    }

    /**
     * 删除hashmap中的值
     * @param key
     * @param item
     */
    public void hdel(String key,Object... item){
        redisTemplate.opsForHash().delete(key,item);
    }

    //======================set==================================

    /**
     * 根据key获取set中所有值
     * @param key
     * @return
     */
    public Set<Object> sget(String key){
        try{
            return redisTemplate.opsForSet().members(key);
        }catch (Exception e){
            logger.info("===RedisUtil.sget()===error:{}",e);
            return null;
        }
    }

    /**
     * 根据value从set中查看是否存在
     * @param key
     * @param value
     * @return
     */
    public boolean shaskey(String key,Object value){
        try{
            return redisTemplate.opsForSet().isMember(key,value);
        }catch (Exception e){
            logger.info("===RedisUtil.shashkey()===error:{}",e);
            return false;
        }
    }

    /**
     * 将set放入缓存
     * @param key
     * @param value
     * @return
     */
    public boolean sset(String key,Object... value){
        return sset(key,-1,value);
    }

    /**
     * 将set存入缓存并设置时间
     * @param key
     * @param time
     * @param value
     * @return
     */
    public boolean sset(String key,long time,Object... value){
        try {
            redisTemplate.opsForSet().add(key,value);
            if(time>0){
                expire(key,time);
            }
            return true;
        } catch (Exception e) {
            logger.info("===RedisUtil.sset()===error:{}",e);
            return false;
        }
    }

    /**
     * 获取set缓存长度
     * @param key
     * @return
     */
    public long sgetSize(String key){
        try{
            return redisTemplate.opsForSet().size(key);
        }catch (Exception e){
            logger.info("==RedisUtil.sgetSize()===error:{}",e);
            return 0;
        }
    }

    /**
     * 移除值为value
     * @param key
     * @param value
     * @return
     */
    public long sremove(String key,Object... value){
        try {
            return redisTemplate.opsForSet().remove(key,value);
        }catch (Exception e){
            logger.info("===RedisUtil.sremove()===error:{}",e);
            return 0;
        }
    }

    //=================================list=============================

    /**
     * 获取list中所有值
     * @param key
     * @return
     */
    public List<Object> lget(String key){
        return lget(key,0,-1);
    }

    /**
     * 指定获取list中的值
     * @param key
     * @param start
     * @param end
     * @return
     */
    public List<Object> lget(String key,long start,long end){
        try{
            return redisTemplate.opsForList().range(key,start,end);
        }catch (Exception e){
            logger.info("===RedisUtil.lget()===error:{}",e);
            return null;
        }
    }

    /**
     * 获取list缓存的长度
     * @param key 键
     * @return
     */
    public long lGetListSize(String key) {
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            logger.info("===RedisUtil.lGetListSize()===error:{}",e);
            return 0;
        }
    }

    /**
     * 通过索引 获取list中的值
     * @param key 键
     * @param index 索引 index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return
     */
    public Object lGetIndex(String key, long index) {
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将list放入缓存
     * @param key 键
     * @param value 值
     * @return
     */
    public boolean lSet(String key, List<Object> value) {
        return lSet(key,value,-1);
    }

    /**
     * 将list放入缓存
     * @param key 键
     * @param value 值
     * @param time 时间(秒)
     * @return
     */
    public boolean lSet(String key, List<Object> value,long time) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            if(time>0){
                expire(key,time);
            }
            return true;
        } catch (Exception e) {
            logger.info("===RedisUtil.lser()===error:{}",e);
            return false;
        }
    }

    /**
     * 将list放入缓存
     * @param key 键
     * @param value 值
     * @return
     */
    public boolean lSet(String key, Object value) {
        return lSet(key,value,-1);
    }

    /**
     * 将list放入缓存
     * @param key 键
     * @param value 值
     * @param time 时间(秒)
     * @return
     */
    public boolean lSet(String key, Object value, long time) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            if (time > 0)
                expire(key, time);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据索引修改list中的某条数据
     * @param key 键
     * @param index 索引
     * @param value 值
     * @return
     */
    public boolean lUpdateIndex(String key, long index, Object value) {
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 移除N个值为value
     * @param key 键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    public long lRemove(String key, long count, Object value) {
        try {
            Long remove = redisTemplate.opsForSet().remove(key, count, value);
            return remove;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
        System.out.println(sdf.format(new Date(14214)));

    }

}
