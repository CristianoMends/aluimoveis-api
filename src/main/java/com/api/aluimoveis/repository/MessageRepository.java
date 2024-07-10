package com.api.aluimoveis.repository;

import com.api.aluimoveis.dto.MessageDto;
import com.api.aluimoveis.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {


    @Query(value =  "select * from message " +
            "where (recipient_id = :user1 AND sender_id = :user2) OR " +
            "(sender_id = :user1 AND recipient_id = :user2) " +
            "order by timestamp asc", nativeQuery = true)
    List<Message> getMessageBetweenUsers(Long user1, Long user2);

}
