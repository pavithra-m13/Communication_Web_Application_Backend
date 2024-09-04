package com.example.demo.domain;

import java.util.List;

import jakarta.annotation.Generated;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name="groupchat")
public class group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private String groupid;
    public String getGroupid() {
        return groupid;
    }
    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }
    public String getGroupname() {
        return groupname;
    }
    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }
    public String getParticipants() {
        return participants;
    }
    public void setParticipants(String participants) {
        this.participants = participants;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    private String groupname;

    private String participants;
    public group() {
    }
    public group(String groupid, String groupname, String participants) {
        this.groupid = groupid;
        this.groupname = groupname;
        this.participants = participants;
    }
    

}
