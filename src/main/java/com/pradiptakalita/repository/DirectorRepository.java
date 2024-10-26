package com.pradiptakalita.repository;

import com.pradiptakalita.dto.director.DirectorSummaryDTO;
import com.pradiptakalita.entity.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DirectorRepository extends JpaRepository<Director, UUID> {

}
