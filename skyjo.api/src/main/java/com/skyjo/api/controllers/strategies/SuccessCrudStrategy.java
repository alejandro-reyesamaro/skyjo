package com.skyjo.api.controllers.strategies;

import org.springframework.http.ResponseEntity;

import com.skyjo.api.controllers.response.BaseResponse;

public abstract class SuccessCrudStrategy<T extends BaseResponse> implements ICrudResponseStrategy<T> {

    @Override
    public boolean itApplies(T response) {
        return response.isSuccess();
    }

    public abstract ResponseEntity<BaseResponse> run(T response);
}
