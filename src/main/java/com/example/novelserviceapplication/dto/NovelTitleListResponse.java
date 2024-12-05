package com.example.novelserviceapplication.dto;

import com.example.novelserviceapplication.domain.novel.Title;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public class NovelTitleListResponse {

    private final List<NovelTitleInfoResponse> novelTitles;

    public static NovelTitleListResponse responseOf(List<Title> titles) {
        return new NovelTitleListResponse(titles.stream()
                .map(NovelTitleInfoResponse::responseOf)
                .collect(Collectors.toList()));
    }
}
