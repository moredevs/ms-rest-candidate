package com.microservice.rest.candidate.dto;

import lombok.Data;

@Data
public class ApiResponseGeneric<T> {
    private int code;
    private String message;
    private T data;

    public ApiResponseGeneric(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }


}