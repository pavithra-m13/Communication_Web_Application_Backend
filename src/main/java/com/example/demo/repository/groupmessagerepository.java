package com.example.demo.repository;

import org.springframework.stereotype.Repository;
import com.example.demo.domain.groupmessage;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.domain.message;
@Repository
public interface groupmessagerepository extends CrudRepository<groupmessage,Integer>{

    Optional<groupmessage> findByGroupid(String key);
    
}
