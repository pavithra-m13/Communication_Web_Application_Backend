package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.user;
@Repository
public interface userreopository extends CrudRepository<user,Integer>{
    @Query(value="select * from user u where u.phoneno = ?1",nativeQuery = true)
    user findByPhoneno(long phoneno);

    user findByPhonenoAndPassword(long phoneno, String password);

    @Query(value = "SELECT * FROM user u WHERE  u.userid NOT IN (SELECT c.contactid FROM chats c WHERE c.userid = :userid)",nativeQuery = true)
    List<user> findAllUsersNotInContacts(@Param("userid") int userid);
    @Query(value = "select * from user u where u.userid = ?1",nativeQuery = true)
    user findByUserid(int userid);
    @Query(value="select u.username from user u where u.userid = :int1",nativeQuery = true)
    String  findById(@Param("int1") int int1);
    @Query(value="select * from user u where u.userid != ?1",nativeQuery = true)
    List<user> findAllUsersByNotUserid(int userid);
    @Query(value="select * from user u",nativeQuery = true)
    List<user> findAllUsers();

    

}
