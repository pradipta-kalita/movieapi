package com.pradiptakalita.repository;

import com.pradiptakalita.entity.Studio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StudioRepository extends JpaRepository<Studio, UUID> {
}
