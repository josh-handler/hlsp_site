package com.hlsp.hlsp_site.controllers;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
public class LoginRequestController {
    
    @Autowired
    private SiteUserRepository siteUserRepository; 

    // public static final String LOGIN_SUCCESS_VIEW_NAME = "loginSuccess";
    // public static final String LOGIN_FAIL_VIEW_NAME = "loginFailureBadCredentialsOrSqlIssue";
    // public static final String INVALID_DATA_VIEW_NAME = "loginFailureInvalidData";
    public static final String LOGIN_SUCCESS_VIEW_NAME = "index";
    public static final String LOGIN_FAIL_VIEW_NAME = "exception";
    public static final String INVALID_DATA_VIEW_NAME = "exception";

    @PostMapping("/loginRequest")
    public ModelAndView postLoginRequest(@RequestParam(name="email", required=true) String email, 
    @RequestParam(name="password", required=true) String password, HttpSession session, HttpServletResponse response){

        Map<String, Object> model = new HashMap<>();
        if(validLoginDataStructure(email,password)){
            User user = null;
            try{
                user = loginAttempt(email, password);
            }
            catch(SQLException | NoSuchAlgorithmException loginAttemptException){
                model.put("exception", loginAttemptException);
                return new ModelAndView(LOGIN_FAIL_VIEW_NAME, model);
                // return LOGIN_FAIL_VIEW_NAME;
            }
        
            if(user!=null){
            session.setAttribute("user", user);

            Cookie emailCookie = new Cookie("email", user.getEmail());
            emailCookie.setPath("/");
            response.addCookie(emailCookie);

            Cookie displayNameCookie = new Cookie("displayName", user.getDisplayName());
            displayNameCookie.setPath("/");
            response.addCookie(displayNameCookie); 

            Cookie loginStatusCookie = new Cookie("loginStatus", "in");
            loginStatusCookie.setPath("/");
            response.addCookie(loginStatusCookie);

            model.put("loginStatus", "in");

            session.setAttribute("user", user);

            return new ModelAndView("redirect:/" + LOGIN_SUCCESS_VIEW_NAME, model);
            }
        }
        
        return new ModelAndView(INVALID_DATA_VIEW_NAME, model);
    }

    public User loginAttempt(String userName, String password) throws SQLException, NoSuchAlgorithmException
    {
            // SecureRandom saltShaker = new SecureRandom();
            // byte[] salt = new byte[16];
            // saltShaker.nextBytes(salt);

        List<byte[]> resultSetSiteSalts = siteUserRepository.getSaltForLogin(userName);

        if(resultSetSiteSalts.isEmpty())
            throw new SQLException("Wrong email or password -1");

        byte[] salt = resultSetSiteSalts.get(0);
        
        CustomPasswordEncoder encoder = new CustomPasswordEncoder();
        encoder.setSalt(salt);
        encoder.hashWithCurrentSalt(password);

        byte[] hash = encoder.getHash();
        //Retrieve the user, ensure that there is only 1 response

        List<SiteUser> resultSetSiteUsers = siteUserRepository.logInAsUser(userName, hash);
        
        if(resultSetSiteUsers.isEmpty()){
            throw new SQLException("Wrong email or password");
        }

        if(resultSetSiteUsers.size() > 1){
            throw new SQLException("Database error");

        }
        
        User foundUser = resultSetSiteUsers.get(0).toUser();

        return foundUser;
    }

    public Boolean validLoginDataStructure(String userName, String password){
        return true;
    }
}

// Salt:
// -54 1 15 110 126 -85 -81 -14 -96 -65 15 -8 70 31 76 -95
// Hash
// -124 -98 57 74 27 -49 -70 27 -88 -78 117 15 0 49 -31 -60
//
//from password of "password"