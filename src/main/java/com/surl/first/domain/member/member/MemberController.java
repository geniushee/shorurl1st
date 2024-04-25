package com.surl.first.domain.member.member;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.surl.first.domain.member.member.entity.LoginDto;

@RestController
@RequestMapping("/member")
public class MemberController {

	// @PostMapping("/login")
	// public ResponseEntity<?> memberLogin(@RequestBody LoginDto dto){
	//
	// }
}
