package com.example.novelserviceapplication.exception.member;

import com.example.novelserviceapplication.exception.NovelServiceApplicationException;

public class MemberException extends NovelServiceApplicationException {
    public MemberException(String message) {
        super(message);
    }

    public MemberException(String message, Throwable cause) {
        super(message, cause);
    }
}
