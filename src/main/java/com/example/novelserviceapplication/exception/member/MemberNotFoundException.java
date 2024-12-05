package com.example.novelserviceapplication.exception.member;

public class MemberNotFoundException extends MemberException {
    public MemberNotFoundException(String message) {
        super(message);
    }

    public MemberNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
