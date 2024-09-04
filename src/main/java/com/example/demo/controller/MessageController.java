package com.example.demo.controller; 
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.groupmessage;
import com.example.demo.domain.message;
import com.example.demo.service.GroupService;
import com.example.demo.service.messagefileservice;
import com.example.demo.service.messageservice;

@RestController
@CrossOrigin(origins = {"http://localhost:3000","http://localhost:3001"})
public class MessageController {
  @Autowired
    private messageservice ms;
    @Autowired
    private GroupService groupservice;
    @Autowired
    private messagefileservice messageFileService;
	  @GetMapping("messages/conversation/{senderid}/{receiverid}")
    public List<message> getMessagesByConversation(@PathVariable int senderid, @PathVariable int receiverid) throws IOException {
        return messageFileService.getMessagesByConversation(senderid, receiverid);
    }
    
    @GetMapping("/group/{groupId}")
    public List<groupmessage> getMessagesByGroup(@PathVariable String groupId) throws IOException {
        return groupservice.getMessagesForGroup(groupId);
    }


} 
