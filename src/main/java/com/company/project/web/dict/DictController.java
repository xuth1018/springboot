package com.company.project.web.dict;


import com.company.project.core.Result;
import com.company.project.model.dict.Dict;
import com.company.project.service.dict.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dict")
public class DictController{

    @Autowired
    private DictService dictService;

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

}
