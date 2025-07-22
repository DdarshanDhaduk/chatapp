package com.Chat.Chatapp.Service;

import com.Chat.Chatapp.Dto.FrindListDto;
import com.Chat.Chatapp.Dto.Pandingreadmessage;
import com.Chat.Chatapp.Entity.Chatentity;
import com.Chat.Chatapp.Entity.Userentity;
import com.Chat.Chatapp.Repository.Chatrepo;
import com.Chat.Chatapp.Repository.Userreopo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class Userchatservice {

    @Autowired
    private Userreopo userreopo;

    @Autowired
    private Chatrepo chatrepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Userentity getUserByUid(String uid){
        return userreopo.findById(uid).orElseThrow(()-> new RuntimeException("Not Found user"));
    }

    public List<Chatentity> getChatByRid(String rid){
        List<Chatentity> chatentities = chatrepo.findChatByRoomId(rid);
        return chatentities;
    }

    public void saveChat(Chatentity chatentity){
        chatentity.setIsread(false);
        chatrepo.save(chatentity);
    }


    public void saveUser(Userentity userentity){
        Optional<Userentity> existingUser = userreopo.findById(userentity.getUsername());

        if (existingUser.isPresent()){
            throw new IllegalArgumentException("Username already exists " + userentity.getUsername());
        }

        Random random = new Random();
        long uid = Math.abs(random.nextLong() % 1_000_000_0000L);

        userentity.setPassword(passwordEncoder.encode(userentity.getPassword()));
        userentity.setRole("ROLE_USER");
        userentity.setUid(uid);
        userreopo.save(userentity);
    }

    public List<FrindListDto> getLastMessage(String username){

        List<Chatentity> lastMessages = chatrepo.findLastMessages(username);

        List<Pandingreadmessage> unreadCounts = chatrepo.findUnreadCounts(username);

        Map<String, Long> unreadMap = unreadCounts.stream()
                .collect(Collectors.toMap(Pandingreadmessage::getRoomid, Pandingreadmessage::getCount));

        List<FrindListDto> friendList = new ArrayList<>();

        for (Chatentity chat : lastMessages) {
            String friendUsername = chat.getSend().equals(username)
                    ? chat.getReceiver()
                    : chat.getSend();

            long count = unreadMap.getOrDefault(chat.getRoomid(), 0L);

            friendList.add(new FrindListDto(
                    chat.getRoomid(),
                    friendUsername,
                    chat.getMessage(),
                    chat.getLocalDateTime(),
                    count
            ));
        }

        return friendList;
    }


    public List<Chatentity> getLastTimeMessage(String user){
        return chatrepo.findLastMessages(user);
    }

    public List<Pandingreadmessage> getUnReadMessage(String user){
        return chatrepo.findUnreadCounts(user);
    }


}
