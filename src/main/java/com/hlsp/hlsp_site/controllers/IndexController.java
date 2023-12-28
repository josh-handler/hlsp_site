package com.hlsp.hlsp_site.controllers;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class IndexController {

    @GetMapping({"/","/index"})
    public String indexGet(Model model){

        return "index";
    }
}
