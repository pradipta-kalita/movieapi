package com.pradiptakalita.service.studio;


import com.pradiptakalita.dto.studio.StudioRequestDTO;
import com.pradiptakalita.dto.studio.StudioResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface StudioService {
    StudioResponseDTO getStudioById(UUID id);
    List<StudioResponseDTO> getAllStudio();
    StudioResponseDTO createStudio(StudioRequestDTO studioRequestDTO);
    StudioResponseDTO updateStudioById(StudioRequestDTO studioRequestDTO, UUID id);
    void deleteStudioById(UUID id);
}
