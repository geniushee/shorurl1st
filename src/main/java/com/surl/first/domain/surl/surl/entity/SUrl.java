package com.surl.first.domain.surl.surl.entity;

import com.surl.first.domain.member.member.entity.Member;
import com.surl.first.global.config.AppConfig;
import com.surl.first.global.entity.TimeEntity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SUrl extends TimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String origin;
	@Setter
	private String title;
	@Setter
	private String content;
	@ManyToOne
	private Member member;

	public String getShortUrl() {
		if(title != null){
			return AppConfig.getFrontUrl() + "/" + title;
		}
		return AppConfig.getFrontUrl() + "/" + id;
	}
}
