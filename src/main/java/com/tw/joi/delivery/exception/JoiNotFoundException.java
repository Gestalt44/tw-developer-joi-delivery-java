package com.tw.joi.delivery.exception;

public class JoiNotFoundException extends RuntimeException {
    public JoiNotFoundException(String msg) {
        super(msg + " Not found");
    }
    
}
