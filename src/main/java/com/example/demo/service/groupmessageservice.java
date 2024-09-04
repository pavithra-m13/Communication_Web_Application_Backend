package com.example.demo.service;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.groupmessage;
import com.example.demo.repository.groupmessagerepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Service
public class groupmessageservice {

    @Autowired
    private groupmessagerepository groupMessageRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public groupmessageservice(groupmessagerepository groupMessageRepository) {
        this.groupMessageRepository = groupMessageRepository;
    }

    public void saveOrUpdateGroupMessages(String key, List<JsonNode> newMessages) throws JsonProcessingException {
        if (!key.contains("*")) {
            Optional<groupmessage> existingGroupMessageOpt = groupMessageRepository.findByGroupid(key);
            groupmessage groupMsgEntity;

            if (existingGroupMessageOpt.isPresent()) {
                groupMsgEntity = existingGroupMessageOpt.get();
                List<JsonNode> existingMessages = objectMapper.readValue(groupMsgEntity.getGroupmessage(), List.class);
                existingMessages.addAll(newMessages);
                groupMsgEntity.setGroupmessage(objectMapper.writeValueAsString(existingMessages));
            } else {
                groupMsgEntity = new groupmessage();
                groupMsgEntity.setGroupid(key);

                // Convert JsonNode list to a single JSON string excluding unwanted fields
                List<JsonNode> filteredMessages = filterMessages(newMessages);
                groupMsgEntity.setGroupmessage(objectMapper.writeValueAsString(filteredMessages));
            }

            groupMessageRepository.save(groupMsgEntity);
        }
    }

    private List<JsonNode> filterMessages(List<JsonNode> messages) {
        List<JsonNode> filteredMessages = new ArrayList<>();
        for (JsonNode messageNode : messages) {
            // Create a new JsonNode excluding unwanted fields
            ObjectNode filteredNode = ((ObjectNode) messageNode).deepCopy();
            filteredNode.remove("timestamp");
            filteredNode.remove("sendername");
            filteredNode.remove("sender");
            filteredNode.remove("groupid");
            filteredMessages.add(filteredNode);
        }
        return filteredMessages;
    }
}

