package com.skyjo.api.controllers.response;

public class BadRequestResponse extends BaseResponse {

    protected BadRequestResponse(String message, boolean isSuccess) {
        super(message, isSuccess);
    }

    public static BadRequestResponse create() {
        return new BadRequestResponse("[Error] Bad request", false);
    }
}
