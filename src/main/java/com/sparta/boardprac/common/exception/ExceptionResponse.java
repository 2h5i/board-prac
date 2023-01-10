package com.sparta.boardprac.common.exception;

import lombok.Getter;

@Getter
public class ExceptionResponse {
    private int statusCode;
     private String message;

     private ExceptionResponse(int statusCode, String message) {
         this.statusCode = statusCode;
         this.message = message;
     }

     public static ExceptionResponse of(int statusCode, String message) {
         return new ExceptionResponse(statusCode, message);
     }
}
