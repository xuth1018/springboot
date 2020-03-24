package com.company.project.web.dict;


import com.company.project.core.Result;
import com.company.project.core.ResultGenerator;
import com.company.project.model.dict.Dict;
import com.company.project.service.dict.DictService;
import com.company.project.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;


@RestController
@RequestMapping("/dict")
public class DictController {

    @Autowired
    private DictService dictService;
    @Autowired
    private RedisUtil redisUtil;

    @PostMapping("/addDict")
    public Result addDict(@RequestBody List<Dict> dict){
        return dictService.addDict(dict);
    }

    @PostMapping("refreshRedis")
    public Result refreshRedis(){
        return dictService.refreshRedis();
    }

    @PostMapping("getDictFromRedis")
    public Result getDictFromRedis(@RequestParam String key){
        return dictService.getDictFromRedis(key);
    }

    @PostMapping("getRedis")
    public Result getRedis(String key){
        Map<Object,Object> map= redisUtil.hmget(key);
        Map<Object,Object> map1 = new TreeMap<>(map);
        return ResultGenerator.genSuccessResult(map1);
    }

    @PostMapping("putRedis")
    public Result putRedis(){
        List<Dict> list = new ArrayList<>();
        list.add(new Dict("1","A001","数学"));
        list.add(new Dict("1","A002","物理"));
        list.add(new Dict("2","A003","语文"));
        list.add(new Dict("2","A004","政治"));
        list.add(new Dict("2","A005","历史"));
        Map<String, Map<String,String>> map = new HashMap<>();
        for(Dict dict:list){
            if(map.containsKey(dict.getParentCode())){
                map.get(dict.getParentCode()).put(dict.getCode(),dict.getValue());
            }else{
                Map<String,String> map1 = new HashMap();
                map1.put(dict.getCode(),dict.getValue());
                map.put(dict.getParentCode(),map1);
            }
        }
        Set<Map.Entry<String,Map<String,String>>> entry1 = map.entrySet();
        for(Map.Entry<String,Map<String,String>> entry:entry1){
            redisUtil.hmset(entry.getKey(),entry.getValue());
        }
        return ResultGenerator.genSuccessResult(map);
    }


}
