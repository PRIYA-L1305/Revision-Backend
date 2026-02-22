package com.revision.whatsapp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
public class WhatsAppServices {

    @Value("${WHATSAPP_TOKEN}")
    private String token;

    @Value("${WHATSAPP_PHONE_ID}")
    private String phoneId;

    @Value("${WHATSAPP_TO}")
    private String to;

    private final WebClient webClient =
            WebClient.create("https://graph.facebook.com/v18.0");

    public void sendPoll(String question,
                         String optionA,
                         String optionB,
                         String optionC,
                         String optionD) {

        Map<String, Object> poll = Map.of(
                "name", question,
                "options", List.of(
                        Map.of("name", optionA),
                        Map.of("name", optionB),
                        Map.of("name", optionC),
                        Map.of("name", optionD)
                ),
                "multiple_answers", false
        );

        Map<String, Object> requestBody = Map.of(
                "messaging_product", "whatsapp",
                "to", to,
                "type", "poll",
                "poll", poll
        );

        webClient.post()
                .uri("/" + phoneId + "/messages")
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}