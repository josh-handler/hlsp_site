package com.hlsp.hlsp_site.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import com.hlsp.hlsp_site.model.SiteUser;
import com.hlsp.hlsp_site.repository.SiteUserRepository;

@Controller
public class UserPageController {
    
    @Autowired
    private SiteUserRepository siteUserRepository; 

    @GetMapping("/user")
    public String sleepGet(@CookieValue(name="loginStatus", defaultValue="out") String loginStatus, 
    @CookieValue(name="displayName", defaultValue="") String displayName,
    @CookieValue(name="email", defaultValue="") String email,
    Model model){
        model.addAttribute("loginStatus", loginStatus);
        if(loginStatus.equals("in")){
            model.addAttribute("displayName", displayName);
        }

        List<SiteUser> resultSetSiteUsers = siteUserRepository.getUserDetailsByEmail(email);

        if(resultSetSiteUsers.size()!=1){
        model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR);
        model.addAttribute("message", "Error retrieving user details. There were " + resultSetSiteUsers.size()
         + " results for users with the email " + email);
        }

        SiteUser siteUser = resultSetSiteUsers.get(0);

        model.addAttribute("email", siteUser.getEmailAddress());
        model.addAttribute("firstName", siteUser.getFirstName());
        model.addAttribute("lastName", siteUser.getLastName());
        model.addAttribute("displayName", siteUser.getDisplayName());
        

        return "user";
    }

}
