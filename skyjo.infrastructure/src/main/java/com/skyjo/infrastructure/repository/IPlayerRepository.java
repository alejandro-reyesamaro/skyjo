package com.skyjo.infrastructure.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IPlayerRepository extends JpaRepository<PlayerEntity, Long>{
    List<PlayerEntity> findByIsActive(boolean isActive);
}
