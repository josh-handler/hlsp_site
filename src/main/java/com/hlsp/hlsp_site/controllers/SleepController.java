package com.hlsp.hlsp_site.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SleepController {
    
    @GetMapping("/sleep")
    public String sleepGet(Model model){

        return "sleep";
    }

}
