package com.example.novelserviceapplication.service;

import com.example.novelserviceapplication.domain.novel.TitleRepository;
import com.example.novelserviceapplication.dto.NovelTitleInfoResponse;
import com.example.novelserviceapplication.dto.NovelTitleListResponse;
import org.springframework.stereotype.Service;

@Service
public class NovelService {

    private final TitleRepository titleRepository;

    public NovelService(TitleRepository titleRepository) {
        this.titleRepository = titleRepository;
    }

    public NovelTitleListResponse allNovelTitle() {
        return NovelTitleListResponse.responseOf(titleRepository.findAll());
    }

    public NovelTitleInfoResponse detailNovelTitle(Long novelId) {
        return NovelTitleInfoResponse.responseOf(
                titleRepository.findById(novelId)
                        .orElseThrow(() -> new IllegalArgumentException("소설이 존재하지 않습니다: " + novelId)));
    }
}
