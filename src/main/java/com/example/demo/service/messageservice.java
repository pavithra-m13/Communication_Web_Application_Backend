// package com.example.demo.service;

// import java.io.IOException;
// import java.util.ArrayList;
// import java.util.List;
// import java.util.Map;
// import java.util.Optional;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// import com.example.demo.domain.message;
// import com.example.demo.repository.messagerepository;
// import com.fasterxml.jackson.core.JsonProcessingException;
// import com.fasterxml.jackson.databind.JsonNode;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import com.fasterxml.jackson.databind.node.ObjectNode;

// @Service
// public class messageservice {
//     @Autowired
//     private messagerepository mesrepo;

//     public messageservice(messagerepository messageRepository) {
//         this.mesrepo = messageRepository;
//     }

//     @Autowired
//     private messagerepository messageRepository;

//     private final ObjectMapper objectMapper = new ObjectMapper();

//     public void saveOrUpdateMessages(String key, List<JsonNode> newMessages) throws JsonProcessingException {
//         Optional<message> existingMessageOpt = messageRepository.findByKey(key);
//         message msgEntity;
//         if (existingMessageOpt.isPresent()) {
//             msgEntity = existingMessageOpt.get();
//             List<JsonNode> existingMessages = objectMapper.readValue(msgEntity.getMessage(), List.class);
//             existingMessages.addAll(newMessages);
//             msgEntity.setMessage(objectMapper.writeValueAsString(existingMessages));
//         } else {
//             msgEntity = new message();
//             msgEntity.setKey(key);
//             // msgEntity.setReceiver(receiver);

//             // Convert JsonNode list to a single JSON string excluding unwanted fields
//             List<JsonNode> filteredMessages = filterMessages(newMessages);
//             msgEntity.setMessage(objectMapper.writeValueAsString(filteredMessages));
//         }
//         messageRepository.save(msgEntity);
//     }

//     private List<JsonNode> filterMessages(List<JsonNode> messages) {
//         List<JsonNode> filteredMessages = new ArrayList<>();
//         for (JsonNode messageNode : messages) {
//             // Create a new JsonNode excluding unwanted fields
//             ObjectNode filteredNode = ((ObjectNode) messageNode).deepCopy();
//             filteredNode.remove("timestamp");
//             filteredNode.remove("senderName");
//             filteredNode.remove("sender");
//             filteredNode.remove("receiver");
//             filteredMessages.add(filteredNode);
//         }
//         return filteredMessages;
//     }
//     //     public List<message> getMessages(String key) throws IOException {
//     //     return messageFileService.getMessagesByKey(key);
//     // }

// }

package com.example.demo.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.message;
import com.example.demo.repository.messagerepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Service
public class messageservice {

    @Autowired
    private messagerepository messageRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public messageservice(messagerepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public void saveOrUpdateMessages(String key, List<JsonNode> newMessages) throws JsonProcessingException {
        if (key.contains("*")) {
            Optional<message> existingMessageOpt = messageRepository.findByKey(key);
            message msgEntity;

            if (existingMessageOpt.isPresent()) {
                msgEntity = existingMessageOpt.get();
                List<JsonNode> existingMessages = objectMapper.readValue(msgEntity.getMessage(), List.class);
                existingMessages.addAll(newMessages);
                msgEntity.setMessage(objectMapper.writeValueAsString(existingMessages));
            } else {
                msgEntity = new message();
                msgEntity.setKey(key);

                // Convert JsonNode list to a single JSON string excluding unwanted fields
                List<JsonNode> filteredMessages = filterMessages(newMessages);
                msgEntity.setMessage(objectMapper.writeValueAsString(filteredMessages));
            }

            messageRepository.save(msgEntity);
        }
    }

    private List<JsonNode> filterMessages(List<JsonNode> messages) {
        List<JsonNode> filteredMessages = new ArrayList<>();
        for (JsonNode messageNode : messages) {
            // Create a new JsonNode excluding unwanted fields
            ObjectNode filteredNode = ((ObjectNode) messageNode).deepCopy();
            filteredNode.remove("timestamp");
            filteredNode.remove("senderName");
            filteredNode.remove("sender");
            filteredNode.remove("receiver");
            filteredMessages.add(filteredNode);
        }
        return filteredMessages;
    }
}

