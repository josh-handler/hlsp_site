package com.hlsp.hlsp_site.controllers;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hlsp.hlsp_site.model.SiteUser;
import com.hlsp.hlsp_site.model.User;
import com.hlsp.hlsp_site.repository.SiteUserRepository;
import com.hlsp.hlsp_site.support.CustomPasswordEncoder;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
// @Service
public class RegisterRequestController {
    @Autowired
    private SiteUserRepository siteUserRepository; 

    @PostMapping("/registerRequest")
    public ModelAndView registerUser(@RequestParam(required=true) String email, 
    @RequestParam(required=true) String firstName, @RequestParam(required=true) String lastName, 
    @RequestParam(required=true) String password, @RequestParam (required=true) String confirmPassword,
     HttpSession session, HttpServletResponse response){

        Map<String, Object> model = new HashMap<>();
        
        if(!password.equals(confirmPassword)){
            model.put("message", "Passwords do not match");
            //return "registrationFailure";
            return new ModelAndView("exception", model);
        }
        
        CustomPasswordEncoder encoder = new CustomPasswordEncoder();

        encoder.saltAndHash(password);

        byte[] salt = encoder.getSalt();
        byte[] hash = encoder.getHash();
        
        if(!siteUserRepository.getUsersEmailByEmail(email).isEmpty()){
            model.put("message",  "Email is already associated with an account");
            // return "registrationFailure";
            return new ModelAndView("exception", model);
        } 

        SiteUser siteUserToRegister = new SiteUser(firstName, lastName, email, hash, salt);


        siteUserToRegister.setDisplayName(firstName);

        siteUserRepository.save(siteUserToRegister);

        User user = siteUserToRegister.toUser();

        Cookie emailCookie = new Cookie("email", user.getEmail());
            emailCookie.setPath("/");
            response.addCookie(emailCookie);

        Cookie displayNameCookie = new Cookie("displayName", user.getFirstName());
        displayNameCookie.setPath("/");
        response.addCookie(displayNameCookie);

        Cookie loginStatusCookie = new Cookie("loginStatus", "in");
        loginStatusCookie.setPath("/");
        response.addCookie(loginStatusCookie);

        model.put("loginStatus", "in");

        session.setAttribute("user", user);

        return new ModelAndView("redirect:/index", model);
    }
}
