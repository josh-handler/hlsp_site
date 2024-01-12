package com.hlsp.hlsp_site.controllers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.hlsp.hlsp_site.model.SiteUser;
import com.hlsp.hlsp_site.model.SleepEvent;
import com.hlsp.hlsp_site.model.SleepEventDTO;
import com.hlsp.hlsp_site.model.User;
import com.hlsp.hlsp_site.repository.SiteUserRepository;
import com.hlsp.hlsp_site.repository.SleepEventRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class SleepController {
    @Autowired
    SleepEventRepository sleepEventRepository;

    @Autowired
    SiteUserRepository siteUserRepository;

    @GetMapping("/sleep")
    public String sleepGet(@CookieValue(name="loginStatus", defaultValue="out") String loginStatus, 
        @CookieValue(name="displayName", defaultValue="") String displayName,Model model,
        HttpSession session){

        User userDto = (User) session.getAttribute("user");

        if(userDto==null){
            model.addAttribute("loginStatus", "out");
            return "login";
        }

        model.addAttribute("loginStatus", loginStatus);
        model.addAttribute("displayName", displayName);
        return "sleep";
    }

    @GetMapping("/sleepTable")
    public ResponseEntity<List<SleepEventDTO>> getSleepTable(HttpSession session){
        User userDto = (User) session.getAttribute("user");

        if(userDto==null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<SleepEvent> sleepEvents = sleepEventRepository.findBySiteUserUserId(userDto.getUserID());
        List<SleepEventDTO> sleepEventDtos = new ArrayList<SleepEventDTO>();

        for(SleepEvent event : sleepEvents){
            sleepEventDtos.add(event.createDto());
        }

        //This sorts the sleep events. First by date, then by time "to bed."
        //Users thus have a (more) consistent experience of the data
        sleepEventDtos.sort(
            Comparator.comparing(SleepEventDTO::getSleepEventDate)
            .thenComparing(SleepEventDTO::getToBedTime));


        ResponseEntity<List<SleepEventDTO>> responseEntity = ResponseEntity.ok(sleepEventDtos);
        return responseEntity;
    }

    @PostMapping("/sleepForm")
    public ResponseEntity<SleepEventDTO> createSleepEvent
    (@RequestBody SleepEventDTO sleepEventdto, HttpSession session) {

        //Not sure why we would create a new sleepevent to save when we have an existing one
        //I think it might be for the @GeneratedValue field?
        User userDto = (User) session.getAttribute("user");

        if(userDto==null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<SiteUser> userList = siteUserRepository.getUserDetailsByEmail(userDto.getEmail());

        if(userList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(userList.size() > 1){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        SleepEvent receivedSleepEvent = new SleepEvent(sleepEventdto, userList.get(0));

        SleepEvent createdSleepEvent = sleepEventRepository.save(receivedSleepEvent);

        //This does not sort the new data into the correct row of the table on the client side
        //but this is a compromise for users knowing the data is in without additional work.
        return new ResponseEntity<>(createdSleepEvent.createDto(), HttpStatus.CREATED);
     }
}
