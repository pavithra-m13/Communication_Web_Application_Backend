package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.example.demo.domain.user;

import com.example.demo.service.logservice;

import jakarta.servlet.http.HttpSession;

@Controller
@CrossOrigin(origins = {"http://localhost:3000","http://localhost:3001"})
public class logcontroller {
    @Autowired
    private logservice service;



    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @RequestMapping(value="/log",method ={RequestMethod.POST})
    public String login(@RequestBody user user1 ,HttpSession session)
    { boolean authenticated = service.authenticate(user1);
        if (authenticated) {
            user completeUser = service.findByPhoneno(user1.getPhoneno());
            int userId = completeUser.getUserid();
//            session.setAttribute("user", completeUser);
            return "success";
        }
        return null;
    }




@PutMapping("/updateprofile")
    public String updateProfile(@RequestBody user updateUser)
{
    user u=new user();
u.setUserid(updateUser.getUserid());
u.setUsername(updateUser.getUsername());
u.setPassword(updateUser.getPassword());
u.setPhoneno(updateUser.getPhoneno());
u.setImage(updateUser.getImage());
 service.updateUserProfile(u);
 return "success";
}

@PutMapping("/forgot-password/{phoneno}/{password}")
    public String updateProfile(@PathVariable long phoneno,@PathVariable String password)
{
    user u=service.findByPhoneno(phoneno);
u.setPassword(password);
 service.updateUserProfile(u);
 return "success";
}

    
}
