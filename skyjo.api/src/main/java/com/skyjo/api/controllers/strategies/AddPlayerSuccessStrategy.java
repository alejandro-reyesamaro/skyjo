package com.skyjo.api.controllers.strategies;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.skyjo.api.controllers.response.AddPlayerResponse;
import com.skyjo.api.controllers.response.BaseResponse;

import static org.springframework.http.HttpStatus.CREATED;

@Component
public class AddPlayerSuccessStrategy extends SuccessCrudStrategy<AddPlayerResponse> {

    @Override
    public ResponseEntity<BaseResponse> run(AddPlayerResponse response) {
        return new ResponseEntity<BaseResponse>(response, CREATED);
    }
}
