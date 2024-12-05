package com.example.novelserviceapplication.dto;

import com.example.novelserviceapplication.domain.novel.Title;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class NovelTitleInfoResponse {

    private final Long id;
    private final Long authorId;
    private final String authorNickname;
    private final String title;
    private final String content;

    public static NovelTitleInfoResponse responseOf(Title title) {
        return new NovelTitleInfoResponse(title.getId(), title.getMember().getId(), title.getMember().getNickname(), title.getTitle(), title.getContent());
    }
}
