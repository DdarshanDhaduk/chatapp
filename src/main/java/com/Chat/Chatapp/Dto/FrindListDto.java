package com.Chat.Chatapp.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FrindListDto {

    private String roomid;
    private String friendUsername;
    private String lastMessage;
    private LocalDateTime lastMessageTime;
    private long unreadCount;
}
