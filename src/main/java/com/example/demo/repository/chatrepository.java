package com.example.demo.repository;

import java.util.List;

import com.example.demo.domain.user;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.chat;

@Repository
public interface chatrepository extends CrudRepository<chat,Integer>{
    @Query(value="select  c.uid,c.userid,c.contactid,c.contactname,u.image from chats c join user u ON c.contactid = u.userid where c.userid = ?1",nativeQuery =true)
    List<chat> findAllByUserid(int userid);
    // message findByMessageid(int messaeid);
    @Query(value = "SELECT c.uid,c.userid,c.contactid,c.contactname " +
                   "FROM chats c " +
                   "JOIN user u ON u.userid = c.contactid " +
                   "WHERE c.userid = :userid AND c.contactname LIKE %:name%", nativeQuery = true)
    List<chat> findByContactnameContainingIgnoreCase(@Param("userid") int userid, @Param("name") String name);
    @Query(value="select c.contactid from chats c where c.userid = :int1",nativeQuery = true)
    List<String> findContactidByUserid(int int1);

}
