package com.anas.scoobergame.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestInternalException extends RuntimeException {

    private String internalMessage;
    private String code;
    private String detialMessage;

    protected RestInternalException() {}

    public RestInternalException(String internalMessage, String code, String detialMessage) {
        this.internalMessage = internalMessage;
        this.code = code;
        this.detialMessage = detialMessage;
    }

}
