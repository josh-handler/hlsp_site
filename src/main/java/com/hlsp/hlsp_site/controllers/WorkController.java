package com.hlsp.hlsp_site.controllers;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
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
import com.hlsp.hlsp_site.model.UserDTO;
import com.hlsp.hlsp_site.model.WorkEvent;
import com.hlsp.hlsp_site.model.WorkEventDTO;
import com.hlsp.hlsp_site.repository.SiteUserRepository;
import com.hlsp.hlsp_site.repository.WorkEventRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class WorkController{
   
    private static final List<String> stressLevelOptions = 
    Arrays.asList("Very Low", "Low", "Medium", "High", "Very High");

    @Autowired SiteUserRepository siteUserRepository;
    @Autowired WorkEventRepository workEventRepository; 

    @GetMapping("/work")
    public String workGet(@CookieValue(name="loginStatus", defaultValue="out") String loginStatus, 
        @CookieValue(name="displayName", defaultValue="") String displayName,Model model,
        HttpSession session){
            
        UserDTO userDto = (UserDTO) session.getAttribute("user");
        if(userDto==null){
            model.addAttribute("loginStatus", "out");
            return "login";
        }
        model.addAttribute("loginStatus", loginStatus);
        model.addAttribute("displayName", displayName);

        return "work";
    }


    @GetMapping("/workTable")
    public ResponseEntity<List<WorkEventDTO>> getSleepTable(HttpSession session){
        UserDTO userDto = (UserDTO) session.getAttribute("user");

        if(userDto==null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<WorkEvent> workEvents = workEventRepository.findBySiteUserUserId(userDto.getUserID());
        List<WorkEventDTO> workEventDtos = new ArrayList<WorkEventDTO>();

        for(WorkEvent event : workEvents){
            workEventDtos.add(event.createDto());
        }

        // This sorts the work events. First by date, then by time time of event start
        // Users thus have a (more) consistent experience of the data
        //TODO if time allows: Move to sorting WorkEvents by Date earlier on, as this is a string sort
        workEventDtos.sort(
            Comparator.comparing(WorkEventDTO::getEventDate)
            .thenComparing(WorkEventDTO::getEventStart));

        ResponseEntity<List<WorkEventDTO>> responseEntity = ResponseEntity.ok(workEventDtos);
        return responseEntity;
    }
    
    @PostMapping("/workForm")
    public ResponseEntity<WorkEventDTO> createSleepEvent
    (@RequestBody WorkEventDTO workEventDto, HttpSession session) {

        UserDTO userDto = (UserDTO) session.getAttribute("user");

        if(userDto==null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }


        if(!stressLevelOptions.contains(workEventDto.getStressLevel())){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<SiteUser> userList = siteUserRepository.getUserDetailsByEmail(userDto.getEmail());

        if(userList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(userList.size() > 1){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try{
        WorkEvent receivedWorkEvent = new WorkEvent(workEventDto, userList.get(0));
        WorkEvent createdWorkEvent = workEventRepository.save(receivedWorkEvent);

        return new ResponseEntity<>(createdWorkEvent.createDto(), HttpStatus.CREATED);
        }
        catch(ParseException exception){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}

