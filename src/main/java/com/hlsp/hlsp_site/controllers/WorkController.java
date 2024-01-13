package com.hlsp.hlsp_site.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import com.hlsp.hlsp_site.model.User;

import jakarta.servlet.http.HttpSession;

@Controller
public class WorkController{
   
    @GetMapping("/work")
    public String workGet(@CookieValue(name="loginStatus", defaultValue="out") String loginStatus, 
        @CookieValue(name="displayName", defaultValue="") String displayName,Model model,
        HttpSession session){
            
        User userDto = (User) session.getAttribute("user");

        // if(userDto==null){
        //     model.addAttribute("loginStatus", "out");
        //     return "login";
        // }

        model.addAttribute("loginStatus", loginStatus);
        model.addAttribute("displayName", displayName);
        return "work";
    }

}

