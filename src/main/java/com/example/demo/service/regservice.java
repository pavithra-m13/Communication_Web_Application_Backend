package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.domain.user;
import com.example.demo.repository.userreopository;

@Service
public class regservice {
    @Autowired
    private  userreopository userrepo;
    PasswordEncoder passwordEncoder;
    // private 
    public void saveuser(String username, long phoneno, String password){
        user u=new user();
        this.passwordEncoder=new BCryptPasswordEncoder();
        String password1=this.passwordEncoder.encode(password);
        u.setUsername(username);
       u.setPhoneno(phoneno);
        u.setPassword(password1);
        System.out.print(u);
        userrepo.save(u);  
    }
    public user findByPhoneno(long phoneno) {
        // TODO Auto-generated method stub
        user u1=userrepo.findByPhoneno(phoneno);
        return u1;
    }
    
   
    public List<user> getUsersAll(int userid) {
          return  (List<user>) userrepo.findAllUsersNotInContacts(userid);
    }
    public List<user> getUserAll() {
        return  (List<user>) userrepo.findAllUsers();
  }
    public List<user> getUsers1All(int userid) {
        return  (List<user>) userrepo.findAllUsersByNotUserid(userid);
  }

    public user findByUserid(int userid) {
        // TODO Auto-generated method stub
            user u2=userrepo.findByUserid(userid);
            return u2;
    }
    public    String getUsername(int int1) {
        // TODO Auto-generated method stub
        String s1=userrepo.findById(int1);
        return s1;
   }
    
    

}

