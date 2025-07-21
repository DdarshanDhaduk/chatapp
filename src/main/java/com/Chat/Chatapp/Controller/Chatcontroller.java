package com.Chat.Chatapp.Controller;

import com.Chat.Chatapp.Entity.Chatentity;
import com.Chat.Chatapp.Entity.Userentity;
import com.Chat.Chatapp.Repository.Chatrepo;
import com.Chat.Chatapp.Service.Userchatservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
public class Chatcontroller {

    @Autowired
    private Userchatservice userchatservice;


    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private Chatrepo chatrepo;

    @MessageMapping("/chat.send")
    public void sendMessage(@Payload Chatentity chatentity){

        Userentity userentity = userchatservice.getUserByUid(chatentity.getReceiver());

        chatentity.setRoomid( generateRoomid(chatentity.getSend(), chatentity.getReceiver()));

        chatentity.setLocalDateTime(LocalDateTime.now());

        userchatservice.saveChat(chatentity);
        messagingTemplate.convertAndSend("/topic/" + chatentity.getRoomid(), chatentity);
    }

    private String generateRoomid(String sender, String receiver){
        List<String> list = Arrays.asList(sender, receiver);
        Collections.sort(list);
        return "room_" + list.get(0) + "_" + list.get(1);
    }

    @GetMapping("/chatmessage")
    public ResponseEntity<?> getChatHistory(@RequestParam String roomId, @RequestParam String user){

        List<Chatentity> chatentities1 = chatrepo.findUnreadMessage(user);
        for (Chatentity c : chatentities1) {
            c.setIsread(true);
            chatrepo.save(c);
        }

        List<Chatentity> chatentities = userchatservice.getChatByRid(roomId);

        if(chatentities.isEmpty()){
            return ResponseEntity.ok(null);
        }

        messagingTemplate.convertAndSend("/topic/seen/" + roomId, chatentities);

        return ResponseEntity.ok(chatentities);
    }


    @MessageMapping("/seen")
    public void seenMessage(@Payload Chatentity chat) {
        if (chat.getRoomid() == null || chat.getSend() == null) return;
        String roomid = chat.getRoomid();
        List<Chatentity> messages = chatrepo.findUnreadMessagesByRoomId(roomid);
        messages.forEach(m -> {
            if (!m.getIsread() && m.getReceiver().equals(chat.getSend())) {
                m.setIsread(true);
                chatrepo.save(m);
            }
        });

        messagingTemplate.convertAndSend("/topic/seen/" + chat.getRoomid(), chat);
    }



    @MessageMapping("/typing")
    public void typingStatus(@Payload String status) {
        messagingTemplate.convertAndSend("/topic/typing/" + status.split(" ")[0], status);
    }


}
