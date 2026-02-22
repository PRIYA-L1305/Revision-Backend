package com.revision.whatsapp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

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

    public void sendMessage(String messageText) {

        Map<String, Object> requestBody = Map.of(
                "messaging_product", "whatsapp",
                "to", to,
                "type", "text",
                "text", Map.of("body", messageText)
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