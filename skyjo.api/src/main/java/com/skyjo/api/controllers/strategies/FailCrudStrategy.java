package com.skyjo.api.controllers.strategies;

import org.springframework.http.ResponseEntity;

import com.skyjo.api.controllers.response.BaseResponse;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public abstract class FailCrudStrategy<T extends BaseResponse> implements ICrudResponseStrategy<T> {

    @Override
    public boolean applies(T response) {
        return !response.isSuccess();
    }

    @Override
    public ResponseEntity<BaseResponse> run(T response) {
        return new ResponseEntity<BaseResponse>(response, INTERNAL_SERVER_ERROR);
    }
}
