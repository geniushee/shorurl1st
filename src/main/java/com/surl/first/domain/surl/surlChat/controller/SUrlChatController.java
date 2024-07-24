package com.surl.first.domain.surl.surlChat.controller;

import com.surl.first.domain.member.member.entity.Member;
import com.surl.first.domain.member.member.service.MemberService;
import com.surl.first.domain.surl.surl.entity.SUrl;
import com.surl.first.domain.surl.surl.service.SUrlService;
import com.surl.first.domain.surl.surlChat.dto.ChatMessageDto;
import com.surl.first.domain.surl.surlChat.dto.ChatRoomCreateReqDto;
import com.surl.first.domain.surl.surlChat.dto.CreatedMessageDto;
import com.surl.first.domain.surl.surlChat.dto.SUrlChatRoomDto;
import com.surl.first.domain.surl.surlChat.entity.SUrlChatRoom;
import com.surl.first.domain.surl.surlChat.service.SUrlChatService;
import com.surl.first.global.config.rabbitmq.StompTemplate;
import com.surl.first.global.securityConfig.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/surlChat")
@RequiredArgsConstructor
public class SUrlChatController {
    private final MemberService memberService;
    private final SUrlChatService sUrlChatService;
    private final SUrlService sUrlService;
    private final StompTemplate template;

    @PostMapping("/createRoom")
    public ResponseEntity<?> createChatRoom(@AuthenticationPrincipal SecurityUser user,
                                            @RequestBody ChatRoomCreateReqDto dto){
        System.out.println("방만들기 surl아이디: " + dto.getSurlId() + " / 방이름 : " + dto.getRoomName());
        Member member = memberService.findById(user.getId());
        SUrl sUrl = sUrlService.findById(dto.getSurlId());
        SUrlChatRoomDto chatRoomDto = sUrlChatService.createRoom(sUrl, member, dto.getRoomName());

        return ResponseEntity.ok(chatRoomDto);
    }

    @GetMapping("/roomList")
    public ResponseEntity showSUrlChatRoomList(
            @RequestParam(name = "page", defaultValue = "1") int page){

        Page<SUrlChatRoomDto> pages = sUrlChatService.findChatRoomsAsPage(page);

        return ResponseEntity.of(Optional.ofNullable(pages));
    }

    @MessageMapping("/chat/{roomId}/createChat")
    @Transactional
    public void createMessage(
            @DestinationVariable(value = "roomId") Long roomId,
            CreatedMessageDto dto,
            Authentication authentication){
        SecurityUser user = (SecurityUser) authentication.getPrincipal();
        Member member = memberService.findById(user.getId());

        ChatMessageDto chatMessageDto = sUrlChatService.createMessage(member, roomId, dto.getBody());
        template.convertAndSend("topic", "chatRoom"+roomId+"MessageCreated", chatMessageDto);
    }

    @GetMapping("/{roomId}/detail")
    public ResponseEntity showChatRoomDetail(
            @PathVariable(name = "roomId") Long roomId
    ){
        SUrlChatRoom room = sUrlChatService.findChatRoomById(roomId).get();
        return ResponseEntity.ok(new SUrlChatRoomDto(room));
    }

    @GetMapping("/{roomId}/messages")
    public ResponseEntity showMessagesInRoom(
            @PathVariable(name = "roomId") Long roomId
    ){
        List<ChatMessageDto> list = sUrlChatService.findAllMessagesByRoomId(roomId);
        return ResponseEntity.ok(list);
    }
}
