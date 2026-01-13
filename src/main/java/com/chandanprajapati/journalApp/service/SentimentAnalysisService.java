package com.chandanprajapati.journalApp.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SentimentAnalysisService {

    @Value("${COHERE_API_KEY}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public SentimentAnalysisService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String generateEmailBody(String journalText,String userName) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        Map<String, Object> body = new HashMap<>();
        body.put("model", "command-r-plus-08-2024");
        body.put("message",
                "Write a warm, supportive email to the user.\n\n" +

                        "Rules:\n" +
                        "- Start email with exactly: Dear " + userName + ",\n" +
                        "- Do NOT use placeholders like [Recipient] or [Your Name]\n" +
                        "- Use caring and professional tone\n" +
                        "- End the email EXACTLY with:\n" +
                        "Best regards,\n" +
                        "Journal App\n\n" +

                        "Include in the email:\n" +
                        "- Detected emotion\n" +
                        "- Short explanation\n" +
                        "- 3 improvement tips\n" +
                        "- Encouraging closing paragraph\n\n" +

                        "Journal entry:\n" +
                        journalText
        );


        body.put("stream", false);
        body.put("chat_history", List.of());

        HttpEntity<Map<String, Object>> request =
                new HttpEntity<>(body, headers);

        ResponseEntity<Map> response =
                restTemplate.postForEntity(
                        "https://api.cohere.ai/v1/chat",
                        request,
                        Map.class
                );

        return response.getBody().get("text").toString();
    }
}
