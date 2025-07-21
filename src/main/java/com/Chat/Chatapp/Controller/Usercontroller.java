package com.Chat.Chatapp.Controller;

import com.Chat.Chatapp.Dto.FrindListDto;
import com.Chat.Chatapp.Entity.Userentity;
import com.Chat.Chatapp.Security.Jwtutill;
import com.Chat.Chatapp.Service.Userchatservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class Usercontroller {

    @Autowired
    private Userchatservice userchatservice;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private Jwtutill jwtutill;

    @PostMapping("/register")
    public ResponseEntity<?> setregister(@RequestBody Userentity userentity){
        userchatservice.saveUser(userentity);

        return ResponseEntity.ok("succesfully register");
    }

    @PostMapping("/login")
    private ResponseEntity<?> Login(@RequestBody Userentity userentity){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userentity.getUsername(), userentity.getPassword()));

        Userentity userentity1 = userchatservice.getUserByUid(userentity.getUsername());
        String token = jwtutill.generateToken(userentity1.getUsername(), userentity1.getRole());

        System.out.println(token);
        return ResponseEntity.ok(Map.of("token", token));
    }

    @GetMapping("/home/{username}")
    public ResponseEntity<?> getUserFriend(@PathVariable String username){

        List<FrindListDto> frindListDtos = userchatservice.getLastMessage(username);

        return ResponseEntity.ok(frindListDtos);
    }
}
