package com.revision.ai;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class OpenAiService {

    @Value("${OPENAI_API_KEY}")
    private String apiKey;

    private final WebClient webClient = WebClient.create("https://api.openai.com/v1");

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

        String requestBody = """
        {
          "model": "gpt-4o-mini",
          "messages": [
            {"role": "user", "content": "%s"}
          ]
        }
        """.formatted(prompt);

        return webClient.post()
                .uri("/chat/completions")
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}