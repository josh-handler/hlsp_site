package com.hlsp.hlsp_site.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import com.hlsp.hlsp_site.model.SiteUser;
import com.hlsp.hlsp_site.model.UserDTO;
import com.hlsp.hlsp_site.repository.SiteUserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserPageController {
    
    @Autowired
    private SiteUserRepository siteUserRepository; 

    @GetMapping("/user")
    public String sleepGet(@CookieValue(name="loginStatus", defaultValue="out") String loginStatus, 
    @CookieValue(name="displayName", defaultValue="") String displayName,
    Model model, HttpSession session){

        UserDTO userDto = (UserDTO) session.getAttribute("user");

        if(userDto==null){
            model.addAttribute("loginStatus", "out");
            return "login";
        }
        //Somewhat legacy, will redo with thymeleaf using cookies to get this if logged in.
        //Also loginStatus shouldn't be needed with use of HttpSession as we don't have cookie-based login atm
        model.addAttribute("loginStatus", loginStatus);
        model.addAttribute("displayName", displayName);

        String email = userDto.getEmail();
        List<SiteUser> resultSetSiteUsers = siteUserRepository.getUserDetailsByEmail(email);

        if(resultSetSiteUsers.size()!=1){
        model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR);
        model.addAttribute("message", "Error retrieving user details. There were " + resultSetSiteUsers.size()
         + " results for users with the email " + email);
        }

        SiteUser siteUser = resultSetSiteUsers.get(0);

        //I think this may have value in case the session User hasn't picked up on a change to the database content
        model.addAttribute("email", siteUser.getEmailAddress());
        model.addAttribute("firstName", siteUser.getFirstName());
        model.addAttribute("lastName", siteUser.getLastName());
        model.addAttribute("displayName", siteUser.getDisplayName());
        

        return "user";
    }

}
