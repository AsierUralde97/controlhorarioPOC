package com.asiertxoo97.controlhorario.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class testController {
    
    @GetMapping("/test")
    public String helloWorld(){
        return "Hello world";
    }

}
