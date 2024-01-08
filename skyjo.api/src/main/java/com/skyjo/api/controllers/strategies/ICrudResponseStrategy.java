package com.skyjo.api.controllers.strategies;

import org.springframework.http.ResponseEntity;

import com.skyjo.api.controllers.response.BaseResponse;

public interface ICrudResponseStrategy<TResponse extends BaseResponse> {
    boolean itApplies(TResponse result);
    ResponseEntity<BaseResponse> run(TResponse result);
}
