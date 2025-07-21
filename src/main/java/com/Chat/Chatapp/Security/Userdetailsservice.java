package com.Chat.Chatapp.Security;

import com.Chat.Chatapp.Entity.Userentity;
import com.Chat.Chatapp.Service.Userchatservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class Userdetailsservice implements UserDetailsService {

    @Autowired
    private Userchatservice userchatservice;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Userentity userentity = userchatservice.getUserByUid(username);
        return new org.springframework.security.core.userdetails.User(
                userentity.getUsername(),
                userentity.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(userentity.getRole())));
    }
}
