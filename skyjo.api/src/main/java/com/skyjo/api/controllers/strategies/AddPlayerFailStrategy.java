package com.skyjo.api.controllers.strategies;

import org.springframework.stereotype.Component;

import com.skyjo.api.controllers.response.AddPlayerResponse;

@Component
public class AddPlayerFailStrategy extends FailCrudStrategy<AddPlayerResponse> {}
