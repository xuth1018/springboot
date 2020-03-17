package com.company.project.service.dict.impl;


import com.alibaba.fastjson.JSON;
import com.company.project.core.Result;
import com.company.project.core.ResultCode;
import com.company.project.core.ServiceException;
import com.company.project.dao.dict.DictMapper;
import com.company.project.model.dict.Dict;
import com.company.project.service.dict.DictService;
import com.company.project.util.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DictServiceImpl implements DictService {

    private static Logger logger = LoggerFactory.getLogger(DictService.class);
    @Resource
    private DictMapper dictMapper;
    @Autowired
    private RedisUtil redisUtil;

    @Transactional
    @Override
    public Result addDict(List<Dict>  dict) {
        logger.info("===DictServiceImpl.addDict()===:info:{}", JSON.toJSONString(dict));
        Result result = new Result();
        result.setCode(ResultCode.SUCCESS).setMessage("添加成功");
        if(dict.size()==0){
            throw new ServiceException("未添加字典");
        }
        try{
            int num = dictMapper.insertList(dict);
            result.setData(num);
        }catch (Exception e){
            logger.info("===DictServiceImpl.addDict()===error:{}",e);
            throw new ServiceException("添加失败");
        }
        return result;
    }

    @Override
    public Result refreshRedis() {
        logger.info("===DictServiceImpl.refreshRedis()===:info:{}");
        Result result = new Result();
        Map<String,List<Dict>> map = new HashMap<>();
        try{
            //查出所有数据
            List<Dict> dict = dictMapper.selectAll();
            if(null!=dict && dict.size()>0){
                for(Dict d:dict){
                    if(map.containsKey(d.getParentCode())){
                        map.get(d.getParentCode()).add(d);
                    }else{
                        List<Dict> l = new ArrayList<>();
                        l.add(d);
                        map.put(d.getParentCode(),l);
                    }
                }
            }
            boolean flag = redisUtil.hmset("DICT",map);
            result.setData(flag).setMessage("刷新成功").setCode(ResultCode.SUCCESS);
        }catch (Exception e){
            logger.info("\"===DictServiceImpl.refreshRedis()===:系统繁忙:{}",e);
            throw new ServiceException("系统繁忙");
        }
        return result;
    }

    @Override
    public Result getDictFromRedis(String key) {
        logger.info("===DictServiceImpl.refreshRedis()===:info:{}",key);
        Result result = new Result().setMessage("查询成功").setCode(ResultCode.SUCCESS);
        try{
            Object map = redisUtil.hget("DICT",key);
            result.setData(map);
        }catch (Exception e){

        }
        return result;
    }

}
