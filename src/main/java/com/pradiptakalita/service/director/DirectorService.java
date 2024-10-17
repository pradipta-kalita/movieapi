package com.pradiptakalita.service.director;

import com.pradiptakalita.dto.director.DirectorRequestDTO;
import com.pradiptakalita.dto.director.DirectorResponseDTO;
import com.pradiptakalita.dto.director.DirectorSummaryDTO;


import java.util.List;
import java.util.UUID;

public interface DirectorService  {
    DirectorResponseDTO createDirector(DirectorRequestDTO directorRequestDTO);
    DirectorResponseDTO updateDirector(DirectorRequestDTO directorRequestDTO,UUID directorId);
    DirectorResponseDTO getDirectorById(UUID id);
    void deleteDirectorById(UUID id);
    List<DirectorSummaryDTO> getAllDirectorWithNameAndProfile();
    List<DirectorResponseDTO> getAllDirectors();

}
