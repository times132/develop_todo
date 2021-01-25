package com.jejujg.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @Value("${spring.redis.host}")
    private String testProfile;

    @GetMapping("/api/home")
    public String Home(Model model){
        return testProfile;
    }
}
