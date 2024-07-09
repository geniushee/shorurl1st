package com.surl.first.domain.surl.srulDocument.repository;

import com.meilisearch.sdk.Index;
import com.surl.first.domain.surl.srulDocument.document.SUrlDocument;
import com.surl.first.domain.surl.surl.dto.SUrlDto;
import com.surl.first.global.config.AppConfig;
import com.surl.first.global.config.meilisearch.MeiliConfig;
import com.surl.first.ut.Ut;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SUrlDocumentRepository {
    private final MeiliConfig meiliConfig;
    private Index sUrlIndex;

    public Index getIndex() {
        if (sUrlIndex == null) sUrlIndex = meiliConfig.meilisearchClient().index("SUrl");
        return sUrlIndex;
    }

    public void create(SUrlDto sUrlDto) {
        SUrlDocument sUrlDoc = new SUrlDocument(sUrlDto);
        String document = Ut.Json.toJson(sUrlDoc);
        getIndex().addDocuments(document);
    }

    public void modify(SUrlDto sUrlDto) {
        SUrlDocument sUrlDoc = new SUrlDocument(sUrlDto);
        String document = Ut.Json.toJson(sUrlDoc);
        getIndex().updateDocuments(document);
    }
}
