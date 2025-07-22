package com.Chat.Chatapp.Repository;

import com.Chat.Chatapp.Entity.Userentity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Userreopo extends JpaRepository<Userentity, String> {
}
