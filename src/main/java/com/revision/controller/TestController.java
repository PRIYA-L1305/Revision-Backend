package com.revision.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins ={"http://localhost:5500",
        "http://127.0.0.1:5500",
        "https://revision-frontend-app.netlify.app/"})
@RestController
public class TestController {

    @GetMapping("/hello")
    public String hello() {
        return "Backend is running!";
    }
}