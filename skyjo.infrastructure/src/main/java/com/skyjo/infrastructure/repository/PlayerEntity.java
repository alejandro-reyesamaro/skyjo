package com.skyjo.infrastructure.repository;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Player")
@Data
public class PlayerEntity {

    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "Name")
    private String name;

    @Column(name = "Email")
    protected String email;

    @Column(name = "ServiceAddress")
    protected String serviceAddress;

    @Column(name = "IsActive")
    protected boolean isActive;

    public PlayerEntity() {}

    public PlayerEntity(String name, String email, String serviceAddress) {
        this.name = name;
        this.email = email;
        this.serviceAddress = serviceAddress;
        this.isActive = false;
    }
}
