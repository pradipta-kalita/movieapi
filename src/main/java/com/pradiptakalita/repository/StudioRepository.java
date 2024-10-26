package com.pradiptakalita.repository;

import com.pradiptakalita.dto.studio.StudioSummaryDTO;
import com.pradiptakalita.entity.Studio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface StudioRepository extends JpaRepository<Studio, UUID> {
}
