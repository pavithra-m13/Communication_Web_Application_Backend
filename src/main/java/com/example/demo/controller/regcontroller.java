package com.example.demo.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


// import com.example.demo.configuration.ChatWebSocketHandler;
import com.example.demo.domain.chat;
import com.example.demo.domain.message;
import com.example.demo.domain.user;
import com.example.demo.service.chatservice;
import com.example.demo.service.messagefileservice;
import com.example.demo.service.messageservice;
import com.example.demo.service.regservice;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpSession;



@RestController
@CrossOrigin(origins = {"http://localhost:3000","http://localhost:3001"})
public class regcontroller {


    @Autowired
    private regservice rg;
    int s;
    @Autowired
    private chatservice cs;
    @Autowired
    private messageservice ms;
    @Autowired
    private messagefileservice messageFileService;

    private final ObjectMapper objectMapper=new ObjectMapper();

    @RequestMapping(value="/reg1",method ={RequestMethod.POST})
    public String login(@RequestBody user user1,HttpSession session,Model model) {

        rg.saveuser(user1.getUsername(),user1.getPhoneno(),user1.getPassword());
        user completeUser = rg.findByPhoneno(user1.getPhoneno());
        session.setAttribute("user", completeUser);
        int userId = completeUser.getUserid();
        session.setAttribute("userId", userId);
        System.out.println("____________________");
        s=(int)session.getAttribute("userId");
        System.out.println("++++"+s);

        return "home";


    }
   


   


}