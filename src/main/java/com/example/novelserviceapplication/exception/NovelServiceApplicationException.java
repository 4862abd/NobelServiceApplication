package com.example.novelserviceapplication.exception;

public class NovelServiceApplicationException extends RuntimeException{

    public NovelServiceApplicationException(String message) {
        super(message);
    }

    public NovelServiceApplicationException(String message, Throwable cause) {
        super(message, cause);
    }
}
