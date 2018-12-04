package com.przemek.zochowski.exceptions;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MyException extends RuntimeException {
    private ErrorCode errorCode;
    private String errorMessage;
    private LocalDateTime errorDateTime;


    public MyException (ErrorCode errorCode, String errorMessage){
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.errorDateTime = LocalDateTime.now();
    }

    @Override
    public String getMessage() {
        return errorCode + " " + errorMessage + " " + errorDateTime;
    }
}
