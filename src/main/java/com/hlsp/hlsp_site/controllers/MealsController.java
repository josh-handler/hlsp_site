package com.hlsp.hlsp_site.controllers;

import java.text.ParseException;
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

import com.hlsp.hlsp_site.model.Meal;
import com.hlsp.hlsp_site.model.MealDTO;
import com.hlsp.hlsp_site.model.Portion;
import com.hlsp.hlsp_site.model.PortionDTO;
import com.hlsp.hlsp_site.model.SiteUser;
import com.hlsp.hlsp_site.model.UserDTO;
import com.hlsp.hlsp_site.repository.MealRepository;
import com.hlsp.hlsp_site.repository.PortionRepository;
import com.hlsp.hlsp_site.repository.SiteUserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class MealsController {
    
    @Autowired
    MealRepository mealRepository;
    @Autowired
    PortionRepository portionRepository;
    @Autowired
    SiteUserRepository siteUserRepository;

    @GetMapping("/meals")
    public String exerciseGet(@CookieValue(name="loginStatus", defaultValue="out") String loginStatus, 
    @CookieValue(name="displayName", defaultValue="") String displayName,Model model,
    HttpSession session){
        UserDTO userDto = (UserDTO) session.getAttribute("user");
        if(userDto==null){
            model.addAttribute("loginStatus", "out");
            return "login";
        }
        model.addAttribute("loginStatus", loginStatus);
        model.addAttribute("displayName", displayName);
        return "meals";
    }

    @GetMapping("/mealsTable")
    public ResponseEntity<List<MealDTO>> getMealsTable(HttpSession session){
        UserDTO userDto = (UserDTO) session.getAttribute("user");

        if(userDto==null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<Meal> mealsList = mealRepository.findBySiteUserUserId(userDto.getUserID());

        List<MealDTO> mealDTOs = new ArrayList<>();

        List<Portion> portionList;

        for(Meal meal : mealsList){
            portionList=portionRepository.findByMealMealGuid(meal.getMealGuid());
            portionList.sort(Comparator.comparing(Portion::getFood));
            mealDTOs.add(meal.createDTO(portionList));
        }

        List<PortionDTO> portions = new ArrayList<>();
        portions.add(new PortionDTO("Bagels", 2));
        portions.add(new PortionDTO("Cream Cheese", 3));
        mealDTOs.add(new MealDTO(portions,"12/01/2024 13:00", "Lunch"));
        // This sorts the work events. First by date, then by time time of event start
        // Users thus have a (more) consistent experience of the data
        //TODO switch to date sort earlier
        mealDTOs.sort(Comparator.comparing(MealDTO::getMealDate));

        ResponseEntity<List<MealDTO>> responseEntity = ResponseEntity.ok(mealDTOs);
        return responseEntity;
    }

    @PostMapping("/mealsForm")
    public ResponseEntity<MealDTO> createMeal
    (@RequestBody MealDTO mealDto, HttpSession session) {

        UserDTO userDto = (UserDTO) session.getAttribute("user");

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
        try{
            Meal recievedMeal = new Meal(mealDto, userList.get(0));
            Meal createdMeal = mealRepository.save(recievedMeal);
            
            return new ResponseEntity<MealDTO>(createdMeal.createDto(), HttpStatus.CREATED);
        }catch(ParseException exception){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
        }

     }

}
