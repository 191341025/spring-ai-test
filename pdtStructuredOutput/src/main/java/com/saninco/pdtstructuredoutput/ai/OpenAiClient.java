package com.saninco.pdtstructuredoutput.ai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OpenAiClient {
    private final ChatClient chatClient;

    public OpenAiClient(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }


    public String askAiForStructuredOutput(String prompt) {
        String response = chatClient.prompt(prompt).call().content();
        System.out.println(response);
        return response;
    }
}

