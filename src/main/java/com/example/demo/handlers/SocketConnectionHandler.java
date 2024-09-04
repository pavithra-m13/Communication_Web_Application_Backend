

package com.example.demo.handlers;

import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import com.example.demo.domain.message;
import com.example.demo.service.chatservice;
import com.example.demo.service.messagefileservice;
import com.example.demo.service.messageservice;
import com.example.demo.service.regservice;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.web.socket.TextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Base64;
import java.util.concurrent.ConcurrentHashMap;
import java.io.IOException;
import java.time.Instant;

public class SocketConnectionHandler extends TextWebSocketHandler {

    private final messageservice messageService;
    @Autowired
    private  messagefileservice messageFileService;
    private final chatservice contactService;
    private final regservice regService;
    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public SocketConnectionHandler(messageservice messageService, messagefileservice messageFileService,chatservice contactService,regservice regService) {
        this.messageService = messageService;
        this.messageFileService = messageFileService;
        this.contactService=contactService;
        this.regService=regService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String userId = extractUserId(session);
        if (userId != null) {
            sessions.put(userId, session);
            // System.out.println(userId);
        }
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println("h");
        String payload = message.getPayload();
        System.out.println(payload);
        try {
                JsonNode data = objectMapper.readTree(payload);
                JsonNode userIdNode = data.get("sender");
                JsonNode conIdNode = data.get("receiver");
                JsonNode senderNode=data.get("sendername");
                JsonNode messagetime=data.get("timestamp");
                JsonNode imagenode=data.get("image");
                String image=imagenode.asText();
                String s1="";
                if(image=="null")
                    s1=null;
                else
                    s1=image;

                String userId = userIdNode.asText();
                String conId = conIdNode.asText();
                String sendern=senderNode.asText();
                String ti=messagetime.asText();
            System.out.println("hello guyd");
                int u=Integer.parseInt(userId);
                int c=Integer.parseInt(conId);
                handleNewMessage(data, userId, conId,sendern,ti,s1);

        } catch (Exception e) {
                System.err.println("Error processing message: " + e.getMessage());
                e.printStackTrace();
        }
    }



    private void handleNewMessage(JsonNode data, String userId, String conId,String sendername,String ti,String image) throws IOException {
        JsonNode mesNode = data.get("message");

        String mes = mesNode.asText();
        message newMessage = new message();
        newMessage.setMessage(mes);
        newMessage.setSender(Integer.parseInt(userId));
        newMessage.setReceiver(Integer.parseInt(conId));
        newMessage.setKey(userId+'*'+conId);
        newMessage.setTimestamp(ti);
        newMessage.setSendername(sendername);
       newMessage.setImage(image);
       System.out.println("hello file image");
        messageFileService.saveMessageToFile(userId,conId,newMessage);

        WebSocketSession recipientSession = sessions.get(conId);
        if (recipientSession != null && recipientSession.isOpen()) {
            recipientSession.sendMessage(new TextMessage(objectMapper.writeValueAsString(data)));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.values().remove(session);
        System.out.println("closed");
    }

    private String extractUserId(WebSocketSession session) {
        String query = session.getUri().getQuery();
        if (query != null) {
            String[] params = query.split("&");
            for (String param : params) {
                String[] keyValue = param.split("=");
                if (keyValue.length == 2 && "userid".equals(keyValue[0])) {
                    return keyValue[1];
                }
            }
        }
        return null;
    }
}