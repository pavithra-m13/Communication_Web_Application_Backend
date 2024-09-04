package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name="groupmessage")
public class groupmessage {
   @Id
    @JsonIgnore
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int gid;

    private String groupmessage;
    private String groupid;
    @Transient
    private String timestamp;
    @Transient
    private String sendername;
    @Transient
    private int sender;

    @Transient
    private String image;

    public groupmessage() {
    }
    public groupmessage(int gid, String groupmessage, String groupid, String timestamp, String sendername, int sender,String image) {
        this.gid = gid;
        this.groupmessage = groupmessage;
        this.groupid = groupid;
        this.timestamp = timestamp;
        this.sendername = sendername;
        this.sender = sender;
        this.image=image;
    }
    public int getGid() {
        return gid;
    }
    public void setGid(int gid) {
        this.gid = gid;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public String getGroupmessage() {
        return groupmessage;
    }
    public void setGroupmessage(String groupmessage) {
        this.groupmessage = groupmessage;
    }
    public String getGroupid() {
        return groupid;
    }
    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }
    public String getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    public String getSendername() {
        return sendername;
    }
    public void setSendername(String sendername) {
        this.sendername = sendername;
    }
    public int getSender() {
        return sender;
    }
    public void setSender(int sender) {
        this.sender = sender;
    }
    
  
}
