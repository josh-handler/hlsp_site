package com.hlsp.hlsp_site.controllers;

import java.net.http.HttpResponse;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hlsp.hlsp_site.model.SiteUser;
import com.hlsp.hlsp_site.repository.SiteUserRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class UpdateDetailsController {
    
    @Autowired
    private SiteUserRepository siteUserRepository; 

    @PostMapping("/updateDetails")
    public String updateDetailsPost(@CookieValue(name="loginStatus", defaultValue="out") String loginStatus, 
    @CookieValue(name="email", defaultValue="") String email, Model model, HttpServletResponse response,
    @RequestParam(name="firstNameForm", required=true) String firstName,
    @RequestParam(name="lastNameForm", required=true) String lastName,
    @RequestParam(name="displayNameForm", required=true) String displayName){
        //Controls which navbar is shown
        model.addAttribute("loginStatus", loginStatus);
        if(loginStatus.equals("in")){
            model.addAttribute("displayName", displayName);
        }

        //Retrieve the user, ensure that there is only 1 response

        List<SiteUser> resultSetSiteUsers = siteUserRepository.getUserDetailsByEmail(email);

        if(resultSetSiteUsers.size()!=1){
        model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR);
        model.addAttribute("message", "Error retrieving user details. There were " + resultSetSiteUsers.size()
         + " results for users with the email " + email);
        }

        SiteUser siteUser = resultSetSiteUsers.get(0);

        //Set the new values and save the change
        siteUser.setFirstName(firstName);
        siteUser.setLastName(lastName);
        siteUser.setDisplayName(displayName);

        siteUserRepository.save(siteUser);

        //Update the model and displayName cookie so the change shows when the user page is reloaded
        model.addAttribute("email", siteUser.getEmailAddress());
        model.addAttribute("firstName", siteUser.getFirstName());
        model.addAttribute("lastName", siteUser.getLastName());

        Cookie displayNameCookie = new Cookie("displayName", siteUser.getDisplayName());
        displayNameCookie.setPath("/");
        response.addCookie(displayNameCookie);        

        return "redirect:/user";
    }

}
