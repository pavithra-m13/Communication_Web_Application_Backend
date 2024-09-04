package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.chat;
import com.example.demo.domain.user;
import com.example.demo.service.chatservice;
import com.example.demo.service.regservice;

import jakarta.servlet.http.HttpSession;
@RestController
@CrossOrigin(origins = {"http://localhost:3000","http://localhost:3001"})
public class UserController {
     @Autowired
    private chatservice cs;
    
    @Autowired
    private regservice rg;
      @RequestMapping(value="/users/userlist/{userid}",method = {RequestMethod.GET})
    public List<user> getUsers1(@PathVariable int userid){
        return rg.getUsersAll(userid);
    }
    @RequestMapping(value="/users/userlist1/{userid}",method = {RequestMethod.GET})
    public List<user> getUsers2(@PathVariable int userid){
        return rg.getUsers1All(userid);
    }
    @RequestMapping(value="/users",method = {RequestMethod.GET})
    public List<user> getUsers(){
        return rg.getUserAll();
    }

    @GetMapping("/users/{phoneno}")
    public user getUsers(@PathVariable long phoneno){
        return rg.findByPhoneno(phoneno);
    }
    @RequestMapping(value="/users/name/{userid}",method = {RequestMethod.GET})
    public user us (@PathVariable int userid){
        return rg.findByUserid(userid);
    }


    @RequestMapping(value="/users/{userid}/{contactedUserId}/addContact",method ={RequestMethod.POST})
    public String addContact(
            @PathVariable int contactedUserId, @PathVariable int userid,
            @RequestBody Map<String, String> body,
            HttpSession session) {
        String contactName = body.get("contactName");

        cs.saveContacts(userid,contactedUserId,contactName);
        return "success";
    }
     @RequestMapping(value="/contacts/{userid}",method = {RequestMethod.GET})
    public List<chat> displayContacts(@PathVariable int userid){

        return cs.getContactsByid(userid);
    }




    @GetMapping("/contacts/{userid}/{search}")
    public List<chat> searchContacts(@PathVariable int userid,@PathVariable String search) {
        return cs.searchContacts(userid,search);
    }
}
