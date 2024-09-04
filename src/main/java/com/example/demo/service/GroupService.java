package com.example.demo.service;

import com.example.demo.domain.group;
import com.example.demo.domain.groupmessage;
import com.example.demo.repository.grouprepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupService {

    @Autowired
    private messagefileservice messageFileService;

    @Autowired
    private grouprepository gerepo;
    // Generate a UUID for the new group
    public void  createGroup(String groupid,String groupname,String participants) {
        group g= new group();
        g.setGroupid(groupid);
        g.setGroupname(groupname);
        g.setParticipants(participants);
        System.out.println(groupname);
        
     //   g.setParticipants(participantsString);
        gerepo.save(g);
    }

    // Send a message to a group
    public void sendMessageToGroup(String groupId, int senderId, String content) {
        groupmessage message = new groupmessage();
        message.setSender(senderId);
        message.setGroupmessage(content);
        message.setTimestamp(LocalDateTime.now().toString());
        message.setGroupid(groupId); // Use UUID as key for group messages

        messageFileService.saveGroupMessageToFile(groupId, message);
    }

    // Retrieve messages for a group
    public List<groupmessage> getMessagesForGroup(String groupId) throws IOException {
        return messageFileService.getMessagesByKey(groupId);
    }

    public List<group> getGroupsByUserId(String userId) {
        // Logic to fetch groups for the user from the database
        return gerepo.findByParticipantsContaining(userId);
    }

    public List<groupmessage> getGroupMessages(String groupId) throws IOException {
        // TODO Auto-generated method stub
            return messageFileService.getMessagesByKey(groupId);
    }
    public String getParticipantsByGroupId(String groupId) {
        // Fetch the group by groupId and return the participants string
        group group = gerepo.findByGroupid(groupId);
        return group.getParticipants();
    }

    public void addMemberToGroup(String groupId, String newMember) {
        group group = gerepo.findByGroupid(groupId);
        String participants = group.getParticipants();
        if (participants != null && !participants.isEmpty()) {
            participants += "," + newMember;
        } else {
            participants = newMember;
        }
        group.setParticipants(participants);
        gerepo.save(group);
    }

    public String getGroupnameByGroupid(String groupid) {
        return gerepo.findGroupnameByGroupid(groupid);
    }

    
}