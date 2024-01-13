package com.hlsp.hlsp_site.controllers;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.hlsp.hlsp_site.model.UserDTO;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
    
    @GetMapping("/login")
    @PostMapping("/login")
    public String loginGet(@CookieValue(name="loginStatus", defaultValue="out") String loginStatus, 
    @CookieValue(name="displayName", defaultValue="") String displayName,Model model,
    HttpSession session){
        UserDTO userDto = (UserDTO) session.getAttribute("user");
        if(userDto==null){
            model.addAttribute("loginStatus", "out");
        }else{
            model.addAttribute("loginStatus", "in");
            model.addAttribute("displayName", displayName);
        }
        return "login";
    }
}
