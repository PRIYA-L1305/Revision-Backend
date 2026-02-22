package com.revision.ai;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
public class OpenRouterService {

    @Value("${OPENROUTER_API_KEY}")
    private String apiKey;

    private final WebClient webClient =
            WebClient.create("https://openrouter.ai/api/v1/chat/completions");

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

        Map<String, Object> message = Map.of(
                "role", "user",
                "content", prompt
        );

        Map<String, Object> requestBody = Map.of(
                "model", "mistralai/mistral-7b-instruct",  // free model
                "messages", List.of(message),
                "max_tokens", 300,
                "temperature", 0.7
        );

        return webClient.post()
                .header("Authorization", "Bearer " + apiKey)
                .header("HTTP-Referer", "https://your-app.com")
                .header("X-Title", "Revision App")
                .header("Content-Type", "application/json")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}