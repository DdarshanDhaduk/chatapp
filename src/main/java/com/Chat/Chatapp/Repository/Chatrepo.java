package com.Chat.Chatapp.Repository;

import com.Chat.Chatapp.Dto.Pandingreadmessage;
import com.Chat.Chatapp.Entity.Chatentity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Chatrepo extends JpaRepository<Chatentity, Long> {

    @Query("SELECT m FROM Chatentity m WHERE m.roomid = :rid ORDER BY m.localDateTime ASC")
    List<Chatentity> findChatByRoomId(@Param("rid") String rid);


    @Query("SELECT c FROM Chatentity c WHERE c.roomid IN " +
            "(SELECT c2.roomid FROM Chatentity c2 WHERE c2.send = :user OR c2.receiver = :user " +
            "GROUP BY c2.roomid HAVING MAX(c2.localDateTime) = c.localDateTime)")
    List<Chatentity> findLastMessages(@Param("user") String user);


    @Query("SELECT new com.Chat.Chatapp.Dto.Pandingreadmessage(c.roomid, COUNT(c)) " +
            "FROM Chatentity c WHERE c.receiver = :user AND c.isread = false " +
            "GROUP BY c.roomid")
    List<Pandingreadmessage> findUnreadCounts(@Param("user") String user);

    @Query("SELECT c FROM Chatentity c WHERE c.receiver = :user AND c.isread = false")
    List<Chatentity> findUnreadMessage(@Param("user") String user);

    @Query("SELECT c FROM Chatentity c WHERE c.roomid = :roomId AND c.isread = false")
    List<Chatentity> findUnreadMessagesByRoomId(@Param("roomId") String roomId);
}
