package com.company.project.web;

import com.company.project.core.Result;
import com.company.project.core.ResultGenerator;
import com.company.project.model.Info;
import com.company.project.service.InfoService;
import com.company.project.util.RedisUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

/**
* Created by CodeGenerator on 2020/03/03.
*/
@RestController
@RequestMapping("/info")
public class InfoController {
    @Resource
    private InfoService infoService;
    @Autowired
    private RedisUtil redisUtil;

    @PostMapping("/add")
    public Result add(Info info) {
        infoService.save(info);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        infoService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    public Result update(Info info) {
        infoService.update(info);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        Info info = infoService.findById(id);
        return ResultGenerator.genSuccessResult(info);
    }

    @PostMapping("/list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<Info> list = infoService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    @PostMapping("/redis")
    public void redis(){
        LocalDateTime dateTime = LocalDateTime.now();
        LocalDateTime lastDay = LocalDateTime.of(dateTime.getYear(),dateTime.getMonth().plus(1),1,0,0);
        Duration duration = Duration.between(dateTime,lastDay);
        long minutes = duration.toMinutes();//相差的分钟数
        long millis = duration.toMillis();//相差毫秒数
        long s = duration.getSeconds();
        System.out.println(s);
        Object id = redisUtil.get("meeting");
        if(null==id){
           redisUtil.set("meeting",1);
        }else{
            long ids = redisUtil.incr("meeting",1);
        }
        redisUtil.expire("meeting",s);

    }

    @PostMapping("/getRedis")
    public Result getRedis(){
        return ResultGenerator.genSuccessResult(redisUtil.get("meeting"));
    }


    public static void main(String[] args) {

    }
}
