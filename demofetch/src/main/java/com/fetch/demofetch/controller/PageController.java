package com.fetch.demofetch.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    String homePage(){
        return "layout/home";
    }

    @GetMapping("/main")
    String mainPage(){
        return "layout/main";
    }

    @GetMapping("/manager")
    public String go(){
        return "messenger";
    }


}
