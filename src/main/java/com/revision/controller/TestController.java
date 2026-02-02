package com.revision.controller;

import com.revision.model.Topic;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins ={"http://localhost:5500",
        "http://127.0.0.1:5500",
        "https://revision-frontend-app.netlify.app/"})
@RestController
public class TestController {

    private List<Topic> topics = new ArrayList<>();

    @GetMapping("/hello")
    public String hello() {
        return "Backend is running!";
    }

    @PostMapping("/topics")
    public void addTopic(@RequestBody Topic topic) {
        topics.add(topic);
    }

    @GetMapping("/topics")
    public List<Topic> getTopics() {
        return topics;
    }
}