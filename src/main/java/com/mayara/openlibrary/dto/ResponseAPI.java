package com.mayara.openlibrary.dto;

public class ResponseAPI {

    private String message;

    public ResponseAPI(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
