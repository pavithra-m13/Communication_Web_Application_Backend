package com.example.demo.repository;


import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.group;
@Repository
public interface grouprepository extends CrudRepository<group,Integer>{
    @Query(value = "select * from groupchat where participants like %?1%", nativeQuery = true)
    List<group> findByParticipantsContaining(String userId);

    group findByGroupid(String groupId);
    @Query(value="select groupname from groupchat where groupid = ?1",nativeQuery = true)
    String findGroupnameByGroupid(String groupid);
    

}
