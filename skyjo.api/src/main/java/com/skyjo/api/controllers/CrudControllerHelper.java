package com.skyjo.api.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.skyjo.api.controllers.response.BadRequestResponse;
import com.skyjo.api.controllers.response.BaseResponse;
import com.skyjo.api.controllers.response.InternalServerErrorResponse;
import com.skyjo.api.controllers.strategies.ICrudResponseStrategy;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Component
public class CrudControllerHelper {
    
    public static <T extends BaseResponse> ResponseEntity<BaseResponse> runStrategies(List<ICrudResponseStrategy<T>> strategies, T result) {
        for(ICrudResponseStrategy<T> s : strategies) {
            if(s.itApplies(result)){
                return s.run(result);
            }
        }
        return new ResponseEntity<>(BadRequestResponse.create(), BAD_REQUEST);
    }

    public static ResponseEntity<BaseResponse> responseForUnhandledException(Exception e) {
        return new ResponseEntity<>(InternalServerErrorResponse.create(e), INTERNAL_SERVER_ERROR);
    }
}
