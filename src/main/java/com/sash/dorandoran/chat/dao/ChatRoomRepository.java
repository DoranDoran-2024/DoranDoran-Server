package com.sash.dorandoran.chat.dao;

import com.sash.dorandoran.chat.domain.ChatRoom;
import com.sash.dorandoran.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    List<ChatRoom> findByUserOrderByCreatedAtDesc(User user);

}
