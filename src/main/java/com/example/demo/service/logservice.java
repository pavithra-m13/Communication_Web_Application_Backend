package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.domain.user;
import com.example.demo.repository.userreopository;

import java.util.Optional;

@Service
public class logservice {
    @Autowired
    private userreopository userrepo;

    PasswordEncoder passwordEncoder;
    public user findByPhoneno(long phoneno) {
        return userrepo.findByPhoneno(phoneno);
    }
    public user log(long phoneno, String password){
        user user = userrepo.findByPhonenoAndPassword(phoneno, password);
        return user;
    }
    public boolean authenticate(user user1) {
        user storedUser = userrepo.findByPhoneno(user1.getPhoneno());
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        if (storedUser.getPhoneno() != 0 && storedUser.getPhoneno()==user1.getPhoneno() &&  encoder.matches(user1.getPassword(), storedUser.getPassword()))
        {
            return true;
        } else {
            return false;
        }
    }


    public  void updateUserProfile(user u){
        user e=userrepo.findByUserid(u.getUserid());
        e.setUsername(u.getUsername());
        e.setPhoneno(u.getPhoneno());
        e.setImage(u.getImage());
        this.passwordEncoder=new BCryptPasswordEncoder();
        String password1=this.passwordEncoder.encode(u.getPassword());
        e.setPassword(password1);
        userrepo.save(e);
    }

    
}
