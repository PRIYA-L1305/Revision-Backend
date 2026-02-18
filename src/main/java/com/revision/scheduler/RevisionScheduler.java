package com.revision.scheduler;

import com.revision.model.Topic;
import com.revision.repository.TopicRepository;
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

    @Scheduled(fixedRate = 60000) // every 1 minute
    public void runRevision() {
        List<Topic> topics = topicRepository.findAll();

        if (topics.isEmpty()) {
            System.out.println("No topics available for revision.");
            return;
        }

        Topic randomTopic = topics.get(new Random().nextInt(topics.size()));
        System.out.println("🔔 Revision Topic: " + randomTopic.getName());
    }
}
