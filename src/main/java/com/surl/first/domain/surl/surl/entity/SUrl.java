package com.surl.first.domain.surl.surl.entity;

import com.surl.first.global.config.Appconfig;
import com.surl.first.global.entity.IdEntity;
import com.surl.first.global.entity.TimeEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
	private String title;
	private String content;

	public String getShortUrl() {
		return Appconfig.getFrontUrl() + "/" + id;
	}
}
