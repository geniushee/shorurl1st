package com.surl.first.domain.surl.surlChat.service;

import com.surl.first.domain.member.member.entity.Member;
import com.surl.first.domain.surl.surl.entity.SUrl;
import com.surl.first.domain.surl.surlChat.dto.ChatMessageDto;
import com.surl.first.domain.surl.surlChat.dto.SUrlChatRoomDto;
import com.surl.first.domain.surl.surlChat.entity.ChatMessage;
import com.surl.first.domain.surl.surlChat.entity.SUrlChatRoom;
import com.surl.first.domain.surl.surlChat.repository.SUrlChatMessageRepository;
import com.surl.first.domain.surl.surlChat.repository.SUrlChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SUrlChatService {
    private final SUrlChatRoomRepository sUrlChatRoomRepository;
    private final SUrlChatMessageRepository sUrlChatMessageRepository;

    @Transactional
    public SUrlChatRoomDto createRoom(SUrl sUrl, Member member, String roomName) {
        SUrlChatRoom room = SUrlChatRoom.builder()
                .sUrl(sUrl)
                .roomName(roomName)
                .creater(member)
                .build();
        room = sUrlChatRoomRepository.save(room);

        return new SUrlChatRoomDto(room);
    }

    public Page<SUrlChatRoomDto> findChatRoomsAsPage(int page) {
        int pageSize = 5;
        List<Sort.Order> orders = new ArrayList<>();
        orders.add(Sort.Order.desc("id"));

        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by(orders));
        return sUrlChatRoomRepository.findAll(pageable).map(SUrlChatRoomDto::new);
    }

    public ChatMessageDto createMessage(Member member, Long roomId, String message) {
        SUrlChatRoom room = sUrlChatRoomRepository.findById(roomId).get();
        ChatMessage createdMessage = ChatMessage.builder()
                .body(message)
                .writer(member)
                .room(room)
                .build();
        return new ChatMessageDto(sUrlChatMessageRepository.save(createdMessage));
    }

    public List<ChatMessageDto> findAllMessagesByRoomId(Long roomId) {
        return sUrlChatMessageRepository.findAllByRoomId(roomId).stream().map(ChatMessageDto::new).toList();
    }

    public Optional<SUrlChatRoom> findChatRoomById(Long roomId) {
        return sUrlChatRoomRepository.findById(roomId);
    }
}
