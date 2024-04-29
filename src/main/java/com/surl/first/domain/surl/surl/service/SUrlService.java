package com.surl.first.domain.surl.surl.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.surl.first.domain.surl.surl.controller.SurlController;
import com.surl.first.domain.surl.surl.entity.SUrl;
import com.surl.first.domain.surl.surl.repository.SUrlRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SUrlService {
	private final SUrlRepository sUrlRepository;

	@Transactional
	public String createNew(String origin) {
		SUrl sUrl = SUrl.builder()
			.origin(origin)
			.build();

		sUrlRepository.save(sUrl);

		return sUrl.getShortUrl();
	}
}
