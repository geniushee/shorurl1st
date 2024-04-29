package com.surl.first.global.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@MappedSuperclass
@Getter
public class TimeEntity extends IdEntity {
	@CreationTimestamp
	private LocalDateTime createDate;
	@UpdateTimestamp
	private LocalDateTime modifyDate;
}
