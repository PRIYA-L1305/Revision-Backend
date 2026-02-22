package com.revision.ai;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
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
                "model", "mistralai/mistral-7b-instruct",
                "messages", List.of(message),
                "max_tokens", 300,
                "temperature", 0.7
        );

        ParameterizedTypeReference<Map<String, Object>> typeRef =
                new ParameterizedTypeReference<>() {};

        Map<String, Object> response = webClient.post()
                .header("Authorization", "Bearer " + apiKey)
                .header("HTTP-Referer", "https://your-app.com")
                .header("X-Title", "Revision App")
                .header("Content-Type", "application/json")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(typeRef)
                .block();

        if (response == null || !response.containsKey("choices")) {
            throw new RuntimeException("Invalid response from OpenRouter");
        }

        List<Map<String, Object>> choices =
                (List<Map<String, Object>>) response.get("choices");

        Map<String, Object> firstChoice = choices.get(0);

        Map<String, Object> messageMap =
                (Map<String, Object>) firstChoice.get("message");

        return (String) messageMap.get("content");
    }
}