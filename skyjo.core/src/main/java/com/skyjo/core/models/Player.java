package com.skyjo.core.models;

import lombok.Data;

@Data
public class Player {
    protected int id;
    protected String name;
    protected String email;
    protected String serviceAddress;
    protected boolean isActive;
}
