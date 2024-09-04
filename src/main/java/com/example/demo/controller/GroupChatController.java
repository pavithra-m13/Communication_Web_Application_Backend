package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;

import com.example.demo.domain.group;
import com.example.demo.domain.groupmessage;
import com.example.demo.domain.message;
import com.example.demo.service.GroupService;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.qos.logback.core.model.Model;

import java.io.IOException;
import java.util.*;

@RestController
@CrossOrigin(origins = {"http://localhost:3000","http://localhost:3001"})
@RequestMapping("/groups")
public class GroupChatController {
    @Autowired
     private final GroupService groupservice;

    public GroupChatController(GroupService gs) {
        this.groupservice = gs;
    }
    String participantsString="";
    @RequestMapping(value="/create",method ={RequestMethod.POST})
    public String createGroup(@RequestBody group group1,Model model) {
System.out.println("hello");
         groupservice.createGroup(group1.getGroupid(),group1.getGroupname(),group1.getParticipants());
         return "success group";
    }
    @RequestMapping(value="/{userId}",method ={RequestMethod.GET})
public List<group> getGroupsByUserId(@PathVariable String userId) {
    return groupservice.getGroupsByUserId(userId);
}

@RequestMapping(value="/name/{groupid}",method ={RequestMethod.GET})
public String getGroupNameByUserId(@PathVariable String groupid) {
    return groupservice.getGroupnameByGroupid(groupid);
}


    @GetMapping("/messages/{groupId}")
    public List<groupmessage> getGroupMessages(@PathVariable String groupId) throws IOException {
        return groupservice.getGroupMessages(groupId);
    }
    @GetMapping("/{groupId}/members")
    public List<String> getGroupMembers(@PathVariable String groupId) throws IOException {
        String participantsString = groupservice.getParticipantsByGroupId(groupId);
        return Arrays.asList(participantsString.split(","));
    }

    @PostMapping("/{groupId}/addMember")
    public String addMember(@PathVariable String groupId, @RequestBody  Map<String,String >request) throws IOException {
        String newMember = request.get("memberIds");
        groupservice.addMemberToGroup(groupId, newMember);
        return "Member added successfully";
    }


    // @MessageMapping("/group/{groupId}")
    // @SendTo("/topic/group/{groupId}")
    // public Message sendMessage(@PathVariable String groupId, Message message) {
    //     groupService.saveMessage(groupId, message);
    //     return message;
    // }
}
