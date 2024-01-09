package com.skyjo.api.controllers.dto;

import lombok.Data;

import org.hibernate.validator.constraints.URL;

import jakarta.validation.constraints.*;

@Data
public class PlayerDto {

    public static final String NAME_REQUIRED_ERR_MSG = "The Player name is required.";
    public static final String NAME_SIZE_ERR_MSG = "The length of the Player name must be between 2 and 100 characters.";
    public static final String EMAIL_REQUIRED_ERR_MSG = "The Player email is required.";
    public static final String EMAIL_FORMAT_ERR_MSG = "The Player email format is not valid";
    public static final String SERVICE_REQUIRED_ERR_MSG = "The Player service-address is required.";
    public static final String SERVICE_FORMAT_ERR_MSG = "The Player service-address format is not valid";

    @NotBlank(message = NAME_REQUIRED_ERR_MSG)
    @Size(min = 2, max = 100, message = NAME_SIZE_ERR_MSG)
    protected String name;

    @NotBlank(message = EMAIL_REQUIRED_ERR_MSG)
    @Email(message = EMAIL_FORMAT_ERR_MSG)
    protected String email;

    @NotBlank(message = SERVICE_REQUIRED_ERR_MSG)
    @URL(message = SERVICE_FORMAT_ERR_MSG) 
    protected String serviceAddress;
}
