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
    private List<String> difficulty = List.of("Easy", "Medium", "Hard");;

    @Autowired
    TopicRepository topicRepository;

    @Autowired
    private OpenRouterService openRouterService;

    @Autowired
    private WhatsAppServices whatsAppServices;

    private final Random random = new Random();

    @Scheduled(cron="0 0 * * * *") // change to cron later
    public void runRevision() {

        try {
            long topicCount = topicRepository.count();

            if (topicCount == 0) {
                System.out.println("No topics available for revision.");
                return;
            }

            // Get all topics and pick random
            List<Topic> topics = topicRepository.findAll();
            Topic randomTopic = topics.get(random.nextInt(topics.size()));

            String difficultyLevel = difficulty.get(random.nextInt(difficulty.size()));

            String fullMCQ = openRouterService.generateMCQ(
                    randomTopic.getName(),
                    difficultyLevel
            );

            String formattedMessage = "🧠 *Revision Time!*\n\n" + fullMCQ;
            if (formattedMessage.length() > 3500) {
                formattedMessage = formattedMessage.substring(0, 3500);
            }

            whatsAppServices.sendMessage(formattedMessage);

            System.out.println("Revision question sent successfully.");

        } catch (Exception e) {
            System.out.println("Error during scheduled revision:");
            e.printStackTrace();
        }
    }
}
