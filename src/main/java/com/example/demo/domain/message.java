package com.example.demo.domain;

import java.time.Instant;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name="messages")
public class message {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
@Column(name = "message",columnDefinition = "LONGTEXT")
    private String message;
    @Column(name="keychat")
    private String key;
    @Transient
    private String timestamp;
    @Transient
    private String sendername;
    @Transient
    private int sender;
    @Transient
    private int receiver;
    @Transient
    private String image;

    public message(String message, String key,int sender,int receiver,String sendername,String t,String image) {
        this.message = message;
        this.key=key;
        this.timestamp=t;
        this.sendername=sendername;
        this.sender=sender;
        this.receiver=receiver;
        this.image=image;
    }


   

    public int getSender() {
        return sender;
    }




    public void setSender(int sender) {
        this.sender = sender;
    }




    public int getReceiver() {
        return receiver;
    }




    public void setReceiver(int receiver) {
        this.receiver = receiver;
    }




    public int getId() {
        return id;
    }




    public void setId(int id) {
        this.id = id;
    }




    public String getMessage() {
        return message;
    }




    public void setMessage(String message) {
        this.message = message;
    }




    public String getKey() {
        return key;
    }




    public void setKey(String key) {
        this.key = key;
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




    public message(){

    }



@JsonProperty("image")
    public String getImage() {
        return image;
    }




    public void setImage(String image) {
        this.image = image;
    }



}