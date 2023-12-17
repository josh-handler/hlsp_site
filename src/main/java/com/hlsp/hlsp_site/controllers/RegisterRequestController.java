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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hlsp.hlsp_site.model.SiteUser;
import com.hlsp.hlsp_site.repository.SiteUserRepository;

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
     HttpSession session){

        Map<String, Object> model = new HashMap<>();
        
        if(!password.equals(confirmPassword)){
            model.put("message", "Passwords do not match");
            //return "registrationFailure";
            return new ModelAndView("exception", model);
        }
        
        SecureRandom saltShaker = new SecureRandom();    
        byte[] salt = new byte[16];
        saltShaker.nextBytes(salt);

        byte[] hash = new byte[16];

        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        try{
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        hash = factory.generateSecret(spec).getEncoded();
        
        if(!siteUserRepository.checkIfEmailIsUsed(email).isEmpty()){
            model.put("message",  "Email is already associated with an account");
            // return "registrationFailure";
            return new ModelAndView("exception", model);
        } 

        SiteUser siteUserToRegister = new SiteUser(firstName, lastName, email, hash, salt);

        siteUserRepository.save(siteUserToRegister);

        }catch(NoSuchAlgorithmException exception){
            model.put("message", exception.getMessage());
            // return "registrationFailure";
            return new ModelAndView("exception", model);
        }catch(InvalidKeySpecException exception){
            model.put("message", exception.getMessage());
            // return "registrationFailure";
            return new ModelAndView("exception", model);
        }
        // return "registrationSuccess";
        model.put("pagecolor","Pink");
        return new ModelAndView("login", model);
    }
}
