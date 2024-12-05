package com.example.novelserviceapplication.controller;

import com.example.novelserviceapplication.service.MemberService;
import com.example.novelserviceapplication.web.dto.SignInRequest;
import com.example.novelserviceapplication.web.dto.SignInResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("${apiPrefix}/authorize")
@RestController
public class AuthController {

    private final MemberService memberService;

    public AuthController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity<SignInResponse> login(@RequestBody SignInRequest signInRequest) {

        return ResponseEntity.ok(new SignInResponse(null, null));
    }
}
