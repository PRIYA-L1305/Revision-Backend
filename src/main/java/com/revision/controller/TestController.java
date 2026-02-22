package com.revision.controller;

import com.revision.ai.OpenRouterService;
import com.revision.model.Topic;
import com.revision.repository.TopicRepository;
import com.revision.scheduler.RevisionScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins ={"http://localhost:5500",
        "http://127.0.0.1:5500",
        "https://revision-frontend-app.netlify.app/"})
@RestController
public class TestController {

    @Autowired
    private RevisionScheduler revisionScheduler;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private OpenRouterService aiService;

    @GetMapping("/generate")
    public String generateTestMCQ() {
        return aiService.generateMCQ("Java Streams", "medium");
    }

    @GetMapping("/hello")
    public String hello() {
        return "Backend is running!";
    }

    @PostMapping("/topics")
    public void addTopic(@RequestBody Topic topic) {
        topicRepository.save(topic);
    }

    @GetMapping("/topics")
    public List<Topic> getTopics() {
        return topicRepository.findAll();
    }
}