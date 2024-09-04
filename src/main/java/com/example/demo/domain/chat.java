package com.example.demo.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="chats")
public class chat {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int uid;
    private int userid;
    private int contactid;
    private String contactname;
    private String image;
    public chat(){

    }
    public chat( int userid, int contactid, String contactname,String image) {
       
        this.userid = userid;
        this.contactid = contactid;
        this.contactname = contactname;
        this.image=image;
    }
    public int getUid() {
        return uid;
    }
    public void setUid(int uid) {
        this.uid = uid;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public int getUserid() {
        return userid;
    }
    public void setUserid(int userid) {
        this.userid = userid;
    }
    public int getContactid() {
        return contactid;
    }
    public void setContactid(int contactid) {
        this.contactid = contactid;
    }
    public String getContactname() {
        return contactname;
    }
    public void setContactname(String contactname) {
        this.contactname = contactname;
    }

    
}
