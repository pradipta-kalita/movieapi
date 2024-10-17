package com.pradiptakalita.repository;

import com.pradiptakalita.dto.director.DirectorResponseDTO;
import com.pradiptakalita.dto.director.DirectorSummaryDTO;
import com.pradiptakalita.entity.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

interface DirectorProjection {
    UUID getId();
    String getName();
    String getProfilePictureUrl();
}


@Repository
public interface DirectorRepository extends JpaRepository<Director, UUID> {
    @Query("SELECT new com.pradiptakalita.dto.director.DirectorSummaryDTO(d.id, d.name, d.profilePictureUrl) FROM Director d")
    List<DirectorSummaryDTO> findAllDirectorsNameAndProfilePicture();


}
