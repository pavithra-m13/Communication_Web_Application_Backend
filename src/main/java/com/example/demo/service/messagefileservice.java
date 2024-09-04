package com.example.demo.service;

// import org.springframework.stereotype.Service;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import com.fasterxml.jackson.core.exc.StreamReadException;
// import com.fasterxml.jackson.core.type.TypeReference;
// import java.io.*;
// import java.util.List;
// import java.util.ArrayList;
// import com.example.demo.domain.message;
// import java.util.HashMap;
// import java.util.Map;

// import com.fasterxml.jackson.databind.DatabindException;
// import com.fasterxml.jackson.databind.JsonNode;


// @Service
// public class messagefileservice {
//     private final ObjectMapper objectMapper = new ObjectMapper();
//     private final String filePath = "C:\\Users\\Pavithra.M\\messages.json";

//     public synchronized void saveMessageToFile(String u,String c,message message) {
//     String key = u + "*" + c;
//     JsonNode data = objectMapper.valueToTree(message);
//      Map<String, JsonNode> existingData = loadExistingData();
//      existingData.put(key,data);
//     saveData(existingData);
//     }

//     public synchronized Map<String,JsonNode> readMessagesFromFile() throws StreamReadException, DatabindException, IOException {
//         // ObjectMapper mapper = new ObjectMapper(); 
//     File from = new File(filePath); 
//         TypeReference<HashMap<String,JsonNode>> typeRef 
//         = new TypeReference<HashMap<String,JsonNode>>() {};
// HashMap<String,JsonNode> o = objectMapper.readValue(from, typeRef);
// return o;

//     }

//     public synchronized void clearFile() {
//         // try {
//         //     // new FileWriter(filePath, false).close();
//         // } catch () {
//         //     // e.printStackTrace();
//         //     System.out.println("Nothing to clear");
//         // }
//     }


//     public  Map<String, JsonNode> loadExistingData() {
//         try {
//             ObjectMapper objectMapper = new ObjectMapper();
//             File file = new File(filePath);
//             if (file.exists()) {
//                 return objectMapper.readValue(file, HashMap.class);
//             }
//         } catch (IOException e) {
//             e.printStackTrace();
//         }
//         return new HashMap<>();
//     }

//     public  void saveData(Map<String, JsonNode> data) {
//         try {
//             ObjectMapper objectMapper = new ObjectMapper();
//             objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), data);
//         } catch (IOException e) {
//             e.printStackTrace();
//         }
//     }

// }
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.*;
import java.time.Instant;
import java.time.LocalDateTime;
import com.fasterxml.jackson.datatype.jdk8.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.JsonNode;
import com.example.demo.domain.groupmessage;
import com.example.demo.domain.message;

@Service
public class messagefileservice {

    private ObjectMapper objectMapper = new ObjectMapper();
    private final String filePath = "C:\\Users\\Pavithra.M\\messages.json";
    public messagefileservice(){
        this.objectMapper=new ObjectMapper();
//        JavaTimeModule module=new JavaTimeModule();
//        module.addSerializer(LocalDateTime.class, new ToStringSerializer());
        objectMapper.registerModule(new JavaTimeModule());

    }

    public synchronized void saveMessageToFile(String u, String c, message message) {
        // Generate a consistent key by always putting the smaller ID first
        String key = Integer.parseInt(u) < Integer.parseInt(c) ? u + "*" + c : c + "*" + u;
//        message.setTimestamp(LocalDateTime.now());
        
        JsonNode messageNode = objectMapper.valueToTree(message);
        Map<String, List<JsonNode>> existingData = loadExistingData();
        existingData.computeIfAbsent(key, k -> new ArrayList<>()).add(messageNode);
        saveData(existingData);
    }

    public Map<String, List<JsonNode>> loadExistingData() {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                TypeReference<HashMap<String, List<JsonNode>>> typeRef = new TypeReference<HashMap<String, List<JsonNode>>>() {};
                return objectMapper.readValue(file, typeRef);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new HashMap<>();
    }

    public void saveData(Map<String, List<JsonNode>> data) {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public synchronized Map<String, List<JsonNode>> readMessagesFromFile() throws StreamReadException, DatabindException, IOException {
        File from = new File(filePath);
        TypeReference<HashMap<String, List<JsonNode>>> typeRef = new TypeReference<HashMap<String, List<JsonNode>>>() {};
        return objectMapper.readValue(from, typeRef);
    }

    public synchronized void clearFile() {
        try {
            new FileWriter(filePath, true).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public List<message> getMessagesByConversation(int senderId, int receiverId) throws IOException {
        String key = senderId < receiverId ? senderId + "*" + receiverId : receiverId + "*" + senderId;
        Map<String, List<JsonNode>> existingData = readMessagesFromFile();
        List<message> messages = new ArrayList<>();
        List<JsonNode> messageNodes = existingData.getOrDefault(key, new ArrayList<>());

        for (JsonNode node : messageNodes) {
            message msg = objectMapper.treeToValue(node, message.class);
            messages.add(msg);
        }

        return messages;
    }
    public List<groupmessage> getMessagesByKey(String key) throws IOException {
        Map<String, List<JsonNode>> existingData = loadExistingData();
        List<JsonNode> messageNodes = existingData.getOrDefault(key, new ArrayList<>());
        List<groupmessage> messages = new ArrayList<>();
        for (JsonNode node : messageNodes) {
            messages.add(objectMapper.treeToValue(node, groupmessage.class));
        }
        return messages;
    }

    public synchronized void saveGroupMessageToFile(String groupId, groupmessage newMessage) {
        String key = groupId;        
        JsonNode messageNode = objectMapper.valueToTree(newMessage);
        Map<String, List<JsonNode>> existingData = loadExistingData();
        existingData.computeIfAbsent(key, k -> new ArrayList<>()).add(messageNode);
        saveData(existingData);
    }

}