package com.hlsp.hlsp_site.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SleepController {
    
    @GetMapping("/sleep")
    public String sleepGet(@CookieValue(name="loginStatus", defaultValue="out") String loginStatus, 
    @CookieValue(name="displayName", defaultValue="") String displayName,Model model){
        model.addAttribute("loginStatus", loginStatus);
        if(loginStatus.equals("in")){
            model.addAttribute("displayName", displayName);
        }
        return "sleep";
    }

}
