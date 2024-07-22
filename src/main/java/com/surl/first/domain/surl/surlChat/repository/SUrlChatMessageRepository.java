package com.surl.first.domain.surl.surlChat.repository;

import com.surl.first.domain.surl.surlChat.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SUrlChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findAllByRoomId(Long roomId);
}
