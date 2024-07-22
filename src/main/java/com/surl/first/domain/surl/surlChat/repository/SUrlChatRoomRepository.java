package com.surl.first.domain.surl.surlChat.repository;

import com.surl.first.domain.surl.surlChat.entity.SUrlChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SUrlChatRoomRepository extends JpaRepository<SUrlChatRoom, Long> {
}
