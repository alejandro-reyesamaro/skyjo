package com.skyjo.api.controllers.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BaseResponse {
    protected String message;
    protected boolean isSuccess;
}
