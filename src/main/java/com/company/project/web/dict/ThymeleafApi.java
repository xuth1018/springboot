package com.company.project.web.dict;

import com.company.project.annotation.Log;
import com.company.project.model.dict.Dict;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/thymeleaf")
public class ThymeleafApi {

    @Log
    @GetMapping("/list")
    public String getDict(Model model){
        List<Dict> list = new ArrayList<>();
        list.add(new Dict("1","A001","数学"));
        list.add(new Dict("1","A002","物理"));
        list.add(new Dict("2","A003","语文"));
        list.add(new Dict("2","A004","政治"));
        list.add(new Dict("2","A005","历史"));
        model.addAttribute("dict",list);
        ObjectMapper objectMapper = new ObjectMapper();
        return "dict";
    }

    @PostMapping("/date")
    @ResponseBody
    public Dict getDate(){
        Dict dict = new Dict();
        dict.setCreateDate(new Date());
        return dict;
    }
}
