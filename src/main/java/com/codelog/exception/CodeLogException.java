package com.codelog.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class CodeLogException extends RuntimeException {

    public final Map<String, String> validation = new HashMap<>();

    public CodeLogException(String message) {
        super(message);
    }

    public CodeLogException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract int statusCode();

    public void addValidation(String fieldName, String message) {
        validation.put(fieldName, message);
    }
}
