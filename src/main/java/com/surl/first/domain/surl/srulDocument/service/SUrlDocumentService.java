package com.surl.first.domain.surl.srulDocument.service;

import com.surl.first.domain.surl.srulDocument.repository.SUrlDocumentRepository;
import com.surl.first.domain.surl.surl.dto.SUrlDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SUrlDocumentService {
    private final SUrlDocumentRepository surlDocumentRepository;

    public void createSUrl(SUrlDto sUrlDto) {
        surlDocumentRepository.create(sUrlDto);
    }

    public void modify(SUrlDto sUrlDto) {
        surlDocumentRepository.modify(sUrlDto);
    }

    public void deleteById(SUrlDto sUrlDto) {
        surlDocumentRepository.deleteById(sUrlDto.getId());
    }
}
