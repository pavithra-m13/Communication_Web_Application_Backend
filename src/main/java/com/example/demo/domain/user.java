package com.example.demo.domain;


import jakarta.persistence.*;


@Entity
@Table(name="user")
public class user {
// name,email,phoneno,password,about,dob,picture
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int userid;
    private long phoneno;

    private String password;
    private String username;
    private String image;

    public int getUserid() {
    return userid;
}
public void setUserid(int userid) {
    this.userid = userid;
}
public long getPhoneno() {
    return phoneno;
}
public void setPhoneno(long phoneno) {
    this.phoneno = phoneno;
}
public String getUsername() {
    return username;
}
public void setUsername(String username) {
    this.username = username;
}


public String getPassword() {
    return password;
}
public void setPassword(String password) {
    this.password = password;
}

   

    public user() {
    }



    public user(String username, long phoneno, String password,String image) {
        this.username = username;
        this.phoneno = phoneno;
        this.password = password;
        this.image = image;

    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    
}
