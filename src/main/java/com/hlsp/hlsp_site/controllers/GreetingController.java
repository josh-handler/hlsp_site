package com.hlsp.hlsp_site.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GreetingController {

	@GetMapping("/greeting")
	public String greetingGet(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
		model.addAttribute("name", name);
		model.addAttribute("pagecolor", "black");
		return "greeting";
	}

	@PostMapping("/greeting")
	public String greetingPost(@RequestParam(name="name", required=false, defaultValue="World") String name,
			@RequestParam(name="pagecolor", required = false, defaultValue = "black") String colorName, Model model){

		model.addAttribute("name", name);
		model.addAttribute("pagecolor", colorName);
		return "greeting";
	}
}