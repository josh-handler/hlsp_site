package com.hlsp.hlsp_site.controllers;

import org.springframework.ui.Model;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandler implements ErrorController  {
    
    public static final String DEFAULT_ERROR_VIEW = "exception";


    @ExceptionHandler(NoHandlerFoundException.class)
    public ModelAndView defaultErrorHandler(NoHandlerFoundException exception){ 
        Map<String, Object> model = new HashMap<>();
        model.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
        model.put("message", exception.getMessage());
        return new ModelAndView(DEFAULT_ERROR_VIEW, model);
    }

    @ExceptionHandler({RuntimeException.class})
    public String handleRuntimeException(RuntimeException exception, Model model) {
        System.out.println(exception.getMessage());
        // ResponseEntity<Object> errorPage=ResponseEntity
        //         .status(HttpStatus.INTERNAL_SERVER_ERROR)
        //         .body(exception.getMessage());
        model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR);
        model.addAttribute("message", exception.getMessage());
        return DEFAULT_ERROR_VIEW;
    }
}
