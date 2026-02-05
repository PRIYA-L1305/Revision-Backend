package com.revision.controller;

import com.revision.model.Topic;
import com.revision.scheduler.RevisionScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins ={"http://localhost:5500",
        "http://127.0.0.1:5500",
        "https://revision-frontend-app.netlify.app/"})
@RestController
public class TestController {

    @Autowired
    private RevisionScheduler revisionScheduler;

    private List<Topic> topics = new ArrayList<>();

    @GetMapping("/hello")
    public String hello() {
        return "Backend is running!";
    }

    @PostMapping("/topics")
    public void addTopic(@RequestBody Topic topic) {
        topics.add(topic);
        revisionScheduler.setTopics(topics);
    }

    @GetMapping("/topics")
    public List<Topic> getTopics() {
        return topics;
    }
}