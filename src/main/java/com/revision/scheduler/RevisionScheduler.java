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

    @Scheduled(fixedRate = 60000) // Change to cron later
    public void runRevision() {

        try {
            String fullMCQ = openRouterService.generateMCQ("Java Streams", "medium");

            if (fullMCQ == null || !fullMCQ.contains("OPTIONS:") || !fullMCQ.contains("ANSWER:")) {
                System.out.println("Invalid MCQ format received from AI.");
                return;
            }

            String question = fullMCQ.split("OPTIONS:")[0]
                    .replace("QUESTION:", "")
                    .trim();

            String optionsBlock = fullMCQ.split("OPTIONS:")[1]
                    .split("ANSWER:")[0]
                    .trim();

            String answer = fullMCQ.split("ANSWER:")[1].trim();

            String[] options = optionsBlock.split("\n");

            if (options.length < 4) {
                System.out.println("AI did not return 4 options.");
                return;
            }

            String optionA = options[0].replace("A)", "").trim();
            String optionB = options[1].replace("B)", "").trim();
            String optionC = options[2].replace("C)", "").trim();
            String optionD = options[3].replace("D)", "").trim();

            whatsAppServices.sendPoll(
                    question,
                    optionA,
                    optionB,
                    optionC,
                    optionD
            );

            System.out.println("Poll sent successfully.");

        } catch (Exception e) {
            System.out.println("Error during scheduled revision:");
            e.printStackTrace();
        }
    }
}
