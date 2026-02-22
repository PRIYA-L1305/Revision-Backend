package com.revision.scheduler;

import com.revision.ai.OpenRouterService;
import com.revision.model.Topic;
import com.revision.repository.TopicRepository;
import com.revision.whatsapp.WhatsAppServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
public class RevisionScheduler {
    private List<Topic> topics;

    @Autowired
    TopicRepository topicRepository;

    @Autowired
    private OpenRouterService openRouterService;

    @Autowired
    private WhatsAppServices whatsAppServices;

    @Scheduled(fixedRate = 60000)//cron = "0 0 * * * *")  // 9am, 3pm, 9pm
    public void runRevision() {
        String fullMCQ = openRouterService.generateMCQ("Java Streams", "medium");

        // Remove answer part
        String questionOnly = fullMCQ.split("ANSWER:")[0].trim();

        whatsAppServices.sendMessage(questionOnly);
    }
}
