package com.example.novelserviceapplication.controller;

import com.example.novelserviceapplication.service.NovelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("${apiPrefix}/novel")
@RestController
public class NovelController {

    private final NovelService novelService;

    public NovelController(NovelService novelService) {
        this.novelService = novelService;
    }

    @GetMapping
    public ResponseEntity allNovelTitle() {
        return ResponseEntity.ok(novelService.allNovelTitle());
    }

    @GetMapping("/{titleId}")
    public ResponseEntity oneNovelTitle(@PathVariable Long titleId) {
        return ResponseEntity.ok(novelService.detailNovelTitle(titleId));
    }
}
