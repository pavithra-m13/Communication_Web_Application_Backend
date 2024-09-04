// package com.example.demo.handlers;

// import org.springframework.web.socket.WebSocketSession;
// import org.springframework.web.socket.handler.TextWebSocketHandler;

// import com.example.demo.domain.groupmessage;
// import com.example.demo.domain.message;
// import com.example.demo.service.contactservice;
// import com.example.demo.service.messagefileservice;
// import com.example.demo.service.messageservice;
// import com.example.demo.service.regservice;
// import com.fasterxml.jackson.databind.JsonNode;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import org.springframework.web.socket.TextMessage;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.socket.CloseStatus;
// import org.springframework.stereotype.Component;

// import java.io.IOException;
// import java.util.List;
// import java.util.Map;
// import java.util.concurrent.ConcurrentHashMap;
// import java.util.ArrayList;

// @Component
// public class groupChatConnectionHandler extends TextWebSocketHandler {

//     private final messageservice messageService;
//     private final messagefileservice messageFileService;
//     private final contactservice contactService;
//     private final regservice regService;
//     private final Map<String, List<WebSocketSession>> groupSessions = new ConcurrentHashMap<>();
//     private final ObjectMapper objectMapper = new ObjectMapper();

//     @Autowired
//     public groupChatConnectionHandler(messageservice messageService, messagefileservice messageFileService, contactservice contactService, regservice regService) {
//         this.messageService = messageService;
//         this.messageFileService = messageFileService;
//         this.contactService = contactService;
//         this.regService = regService;
//     }

//     @Override
//     public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//         String senderId = extractParameter(session, "senderId");
//         String groupId = extractParameter(session, "groupId");
//         if (groupId != null) {
//             groupSessions.computeIfAbsent(groupId, k -> new ArrayList<>()).add(session);
//         }
//     }

//     @Override
//     public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//         String payload = message.getPayload();
//         try {
//             JsonNode data = objectMapper.readTree(payload);
//             String senderId = data.get("senderId").asText();
//             String groupId = data.get("receiverId").asText();
//             String senderName = data.get("senderName").asText();
//             String timestamp = data.get("timestamp").asText();
//             String content = data.get("content").asText();

//             handleNewGroupMessage(senderId, groupId, senderName, timestamp, content);
//         } catch (Exception e) {
//             System.err.println("Error processing message: " + e.getMessage());
//             e.printStackTrace();
//         }
//     }

//     private void handleNewGroupMessage(String senderId, String groupId, String senderName, String timestamp, String content) throws IOException {
//         groupmessage newMessage = new groupmessage();
//         newMessage.setGroupmessage(content);
//         newMessage.setSender(Integer.parseInt(senderId));
//         newMessage.setGroupid(groupId);
//         newMessage.setTimestamp(timestamp);
//         newMessage.setSendername(senderName);

//         // messageFileService.saveGroupMessageToFile(senderId, groupId, newMessage);

//         List<WebSocketSession> sessions = groupSessions.get(groupId);
//         if (sessions != null) {
//             for (WebSocketSession session : sessions) {
//                 if (session != null && session.isOpen()) {
//                     session.sendMessage(new TextMessage(objectMapper.writeValueAsString(data)));
//                 }
//             }
//         }
//     }

//     @Override
//     public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
//         String groupId = extractParameter(session, "groupId");
//         if (groupId != null) {
//             groupSessions.getOrDefault(groupId, new ArrayList<>()).remove(session);
//         }
//     }

//     private String extractParameter(WebSocketSession session, String parameterName) {
//         String query = session.getUri().getQuery();
//         if (query != null) {
//             String[] params = query.split("&");
//             for (String param : params) {
//                 String[] keyValue = param.split("=");
//                 if (keyValue.length == 2 && parameterName.equals(keyValue[0])) {
//                     return keyValue[1];
//                 }
//             }
//         }
//         return null;
//     }
// }
package com.example.demo.handlers;

import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import com.example.demo.domain.groupmessage;
import com.example.demo.service.GroupService;
import com.example.demo.service.chatservice;
import com.example.demo.service.messagefileservice;
import com.example.demo.service.messageservice;
import com.example.demo.service.regservice;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.TextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.ArrayList;



@Component
public class groupChatConnectionHandler extends TextWebSocketHandler {

    @Autowired
    private messageservice messageService;

    @Autowired
    private messagefileservice messageFileService;

    @Autowired
    private final chatservice contactService;

    @Autowired
     private final regservice regService;

    @Autowired
    private final GroupService groupService; // Service to get group members

    private final Map<String, List<WebSocketSession>> groupSessions = new ConcurrentHashMap<>();
    private final Map<String, WebSocketSession> userSessions = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String userId = extractParameter(session, "senderId");
        String groupId = extractParameter(session, "groupId");

        if (userId != null) {
            userSessions.put(userId, session);
        }

        if (groupId != null) {
            groupSessions.computeIfAbsent(groupId, k -> new ArrayList<>()).add(session);
        }
    }


    public groupChatConnectionHandler(messageservice messageService, messagefileservice messageFileService, chatservice contactService, regservice regService,GroupService gs) {
        this.messageService = messageService;
        this.messageFileService = messageFileService;
        this.contactService = contactService;
        this.regService = regService;
        this.groupService=gs;
    }



    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        try {
            JsonNode data = objectMapper.readTree(payload);
            String senderId = data.get("sender").asText();
            String groupId = data.get("groupid").asText();
            String senderName = data.get("sendername").asText();
            String timestamp = data.get("timestamp").asText();
            String content = data.get("groupmessage").asText();
            String image=data.get("image").asText();
            String s1="";
            if(image=="null")
                s1=null;
            else
                s1=image;

            handleNewGroupMessage(data, senderId, groupId, senderName, timestamp, content,s1);
        } catch (Exception e) {
            System.err.println("Error processing message: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void handleNewGroupMessage(JsonNode data, String senderId, String groupId, String senderName, String timestamp, String content,String image) throws IOException {
        // Save the message to file or database
        groupmessage newMessage = new groupmessage();
        newMessage.setGroupmessage(content);
        newMessage.setSender(Integer.parseInt(senderId));
        newMessage.setGroupid(groupId);
        newMessage.setTimestamp(timestamp);
        newMessage.setSendername(senderName);
        newMessage.setImage(image);
        messageFileService.saveGroupMessageToFile(groupId, newMessage);

        // Retrieve group members as a comma-separated string
        String groupMembersString = groupService.getParticipantsByGroupId(groupId); // Assume this returns "1,2,3"
        List<WebSocketSession> sessions = groupSessions.get(groupId);
        if (sessions != null) {
            for (WebSocketSession session : sessions) {
                if (session != null && session.isOpen()) {
                    String sessionSenderId = extractParameter(session, "senderId");
                    if (!senderId.equals(sessionSenderId)) {
                        session.sendMessage(new TextMessage(objectMapper.writeValueAsString(data)));
                        System.out.println("Sent message to session: " + session.getId());
                    }
                }
            }
        }
       

        for (Map.Entry<String, WebSocketSession> entry : userSessions.entrySet()) {
            if (!entry.getKey().equals(senderId) && !isUserInGroup(entry.getKey(), groupId)) {
                WebSocketSession userSession = entry.getValue();
                if (userSession.isOpen()) {
                    userSession.sendMessage(new TextMessage(objectMapper.writeValueAsString(data)));
                }
                // Optional: store message in database or use a push notification service
            }
        }


    }
    
    private boolean isUserInGroup(String userId, String groupId) {
        List<WebSocketSession> sessions = groupSessions.get(groupId);
        if (sessions != null) {
            for (WebSocketSession session : sessions) {
                String sessionUserId = extractParameter(session, "senderId");
                if (userId.equals(sessionUserId)) {
                    return true;
                }
            }
        }
        return false;
    }

    
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String userId = extractParameter(session, "senderId");
        if (userId != null) {
            userSessions.remove(userId);
        }

        String groupId = extractParameter(session, "groupId");
        if (groupId != null) {
            List<WebSocketSession> sessions = groupSessions.get(groupId);
            if (sessions != null) {
                sessions.remove(session);
                if (sessions.isEmpty()) {
                    groupSessions.remove(groupId);
                }
            }
        }
    }


    private String extractParameter(WebSocketSession session, String parameterName) {
        String query = session.getUri().getQuery();
        if (query != null) {
            String[] params = query.split("&");
            for (String param : params) {
                String[] keyValue = param.split("=");
                if (keyValue.length == 2 && parameterName.equals(keyValue[0])) {
                    return keyValue[1];
                }
            }
        }
        return null;
    }
}
