package com.example.demo.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.message;
@Repository
public interface messagerepository extends CrudRepository<message,Integer>{
    Optional<message> findByKey( String key);

}