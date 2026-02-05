package com.revision.scheduler;

import com.revision.model.Topic;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
public class RevisionScheduler {
    private List<Topic> topics;

    // TEMP setter (we'll improve later)
    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }

    @Scheduled(fixedRate = 60000) // every 1 minute
    public void runRevision() {
        if (topics == null || topics.isEmpty()) {
            System.out.println("No topics available for revision.");
            return;
        }

        Topic randomTopic = topics.get(new Random().nextInt(topics.size()));
        System.out.println("🔔 Revision Topic: " + randomTopic.getName());
    }
}
