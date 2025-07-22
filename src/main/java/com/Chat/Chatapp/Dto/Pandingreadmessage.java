package com.Chat.Chatapp.Dto;


import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pandingreadmessage {

    private String roomid;
    private long count;

}
