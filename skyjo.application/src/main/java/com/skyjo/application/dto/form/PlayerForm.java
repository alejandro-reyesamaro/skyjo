package com.skyjo.application.dto.form;

import lombok.Data;

@Data
public class PlayerForm {
    protected String name;
    protected String email;
    protected String serviceAddress;

    public PlayerForm() {
        name = "";
        email = "";
        serviceAddress = "";
    }

    public PlayerForm(String name, String email, String serviceAddress) {
        this.name = name;
        this.email = email;
        this.serviceAddress = serviceAddress;
    }
}
