package com.hlsp.hlsp_site.controllers;
import org.springframework.ui.Model;

import java.net.http.HttpRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class LogoutController {

    @PostMapping({"/logout"})
    public String logoutPost(@CookieValue(name="loginStatus", defaultValue="out") String loginStatus, 
    @CookieValue(name="displayName", defaultValue="") String displayName,Model model, 
    HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession(false);
        if(session!=null){
            session.invalidate();
        }
        Cookie loginStatusCookie = new Cookie("loginStatus", "out");
        loginStatusCookie.setPath("/");
        response.addCookie(loginStatusCookie);
        
        return "redirect:/index";
    }
}
