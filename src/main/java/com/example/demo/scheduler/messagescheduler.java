package com.example.demo.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.demo.service.groupmessageservice;
import com.example.demo.service.messagefileservice;
import com.example.demo.service.messageservice;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.demo.domain.message;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@Component
public class messagescheduler {
    @Autowired
    private final messageservice messageService;

    @Autowired
    private final groupmessageservice gms;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private final messagefileservice messageFileService;
    

    public messagescheduler(messageservice messageService, messagefileservice messageFileService, groupmessageservice gms) {
        this.messageService = messageService;
        this.messageFileService = messageFileService;
        this.gms=gms;
    }
    @Scheduled(fixedRate = 60000) // Run every 60 seconds
    public void saveMessagesToDatabase() throws IOException {
        Map<String, List<JsonNode>> existingData = messageFileService.readMessagesFromFile();

        if (!existingData.isEmpty()) {
            for (Map.Entry<String, List<JsonNode>> entry : existingData.entrySet()) {
                String key = entry.getKey();
                List<JsonNode> messages = entry.getValue();

                // Split the key into sender and receiver
                // String[] ids = key.split("\\*");
                // int sender = Integer.parseInt(ids[0]);
                // int receiver = Integer.parseInt(ids[1]);

                // Save or update the messages
                if(key.contains("*"))
                    messageService.saveOrUpdateMessages(key, messages);
                else
                    gms.saveOrUpdateGroupMessages(key, messages);
                    
            }
        }
    }

}