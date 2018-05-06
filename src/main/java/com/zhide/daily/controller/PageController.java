package com.zhide.daily.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author wuchenxi
 * @date 2018/4/23
 */
@Controller
public class PageController {

    @GetMapping("/")
    public String toLogin(){
        return "login";
    }

    @GetMapping("/index")
    public String toIndex(){
        return "index";
    }

    @GetMapping("/add-daily")
    public String addDaily(){
        return "addDaily";
    }

    @GetMapping("/view-daily")
    public String viewDaily(){
        return "viewDaily";
    }
}
