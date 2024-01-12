package com.hlsp.hlsp_site.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hlsp.hlsp_site.model.SiteUser;
import com.hlsp.hlsp_site.model.User;
import com.hlsp.hlsp_site.repository.SiteUserRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class UpdateDetailsController {
    
    @Autowired
    private SiteUserRepository siteUserRepository; 

    @PostMapping("/updateDetails")
    public String updateDetailsPost(@CookieValue(name="loginStatus", defaultValue="out") String loginStatus, 
    Model model,
    HttpServletResponse response, HttpSession session,
    @RequestParam(name="firstNameForm", required=true) String firstName,
    @RequestParam(name="lastNameForm", required=true) String lastName,
    @RequestParam(name="displayNameForm", required=true) String displayName){
        
        User userDto = (User) session.getAttribute("user");

        if(userDto==null){
            model.addAttribute("loginStatus", "out");
            return "login";
        }
        //Controls which navbar is shown. Should be replaced if there's time
        model.addAttribute("loginStatus", loginStatus);
        model.addAttribute("displayName", displayName);

        //Retrieve the user, ensure that there is only 1 response

        List<SiteUser> resultSetSiteUsers = siteUserRepository.getUserDetailsByEmail(userDto.getEmail());

        if(resultSetSiteUsers.size()!=1){
        model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR);
        model.addAttribute("message", "Error retrieving user details. There were " + resultSetSiteUsers.size()
         + " results for users with the email " + userDto.getEmail());
        }

        SiteUser siteUser = resultSetSiteUsers.get(0);

        //Set the new values and save the change
        siteUser.setFirstName(firstName);
        siteUser.setLastName(lastName);
        siteUser.setDisplayName(displayName);

        siteUser = siteUserRepository.save(siteUser);

        //Replace the HttpSession's User object with a new User that has the current values 
        userDto = siteUser.toUser();
        session.setAttribute("user", userDto);

        //Update the model and displayName cookie so the change shows when the user page is reloaded
        //Might be better replaced by getting session attributes
        model.addAttribute("email", siteUser.getEmailAddress());
        model.addAttribute("firstName", siteUser.getFirstName());
        model.addAttribute("lastName", siteUser.getLastName());

        Cookie displayNameCookie = new Cookie("displayName", siteUser.getDisplayName());
        displayNameCookie.setPath("/");
        response.addCookie(displayNameCookie);        

        return "redirect:/user";
    }

}
