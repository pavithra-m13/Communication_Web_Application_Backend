package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.domain.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.chat;
import com.example.demo.repository.chatrepository;
import com.example.demo.repository.userreopository;

@Service
public class chatservice {
    @Autowired
    private chatrepository conrepo;
    @Autowired
    private userreopository userrepo;
    public void saveContacts(int u, int contactedUserId, String contactName) {
         
        chat m=new chat();
        chat m1=new chat();
        m.setUserid(u);
        m.setContactid(contactedUserId);
        m.setContactname(contactName);
        conrepo.save(m);
        m1.setUserid(contactedUserId);
        m1.setContactid(u);
       user u1= userrepo.findByUserid(u);
        m1.setContactname(u1.getUsername());
        conrepo.save(m1);
    }
    public List<chat> getContactsByid(int userid) {
        // TODO Auto-generated method stub
        
             return  (List<chat>)  conrepo.findAllByUserid(userid);

    }
    public List<chat> searchContacts(int userid,String query) {
        return conrepo.findByContactnameContainingIgnoreCase(userid,query);
    }
    public List<String> getContactIdsByUserId(int int1) {
        // TODO Auto-generated method stub
        return (List<String>)conrepo.findContactidByUserid(int1);
    }

    
}
