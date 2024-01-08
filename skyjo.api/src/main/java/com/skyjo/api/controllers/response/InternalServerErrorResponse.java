package com.skyjo.api.controllers.response;

public class InternalServerErrorResponse extends BaseResponse {

    protected InternalServerErrorResponse(String message, boolean isSuccess) {
        super(message, isSuccess);
    }

    public static InternalServerErrorResponse create(Exception e) {
        return new InternalServerErrorResponse("[Exception] " + e.getMessage(), false);
    }
}
