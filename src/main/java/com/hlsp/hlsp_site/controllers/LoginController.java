package com.hlsp.hlsp_site.controllers;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    
    @GetMapping("/login")
    @PostMapping("/login")
    public String loginGet(@RequestParam(name="pagecolor", required = false, defaultValue = "purple") String colorName, Model model){

        model.addAttribute("pagecolor", colorName);
        return "login";
    }
}
