package com.revision.ai;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
public class HuggingFaceService {

    @Value("${HF_API_KEY}")
    private String apiKey;

    private final WebClient webClient =
            WebClient.create("https://api-inference.huggingface.co/models/mistralai/Mistral-7B-Instruct-v0.2");

    public String generateMCQ(String topic, String difficulty) {

        String prompt = """
                Generate ONE multiple choice question from topic: %s.
                Difficulty: %s.

                Format EXACTLY like this:

                QUESTION:
                <question text>

                OPTIONS:
                A) ...
                B) ...
                C) ...
                D) ...

                ANSWER:
                <only letter>
                """.formatted(topic, difficulty);

        Map<String, Object> requestBody = Map.of(
                "inputs", prompt
        );

        return webClient.post()
                .header("Authorization", "Bearer " + apiKey)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}