package com.pradiptakalita.service.studio;

import com.pradiptakalita.dto.studio.StudioRequestDTO;
import com.pradiptakalita.dto.studio.StudioResponseDTO;
import com.pradiptakalita.entity.Studio;
import com.pradiptakalita.mapper.StudioMapper;
import com.pradiptakalita.repository.StudioRepository;
import com.pradiptakalita.service.cloudinary.CloudinaryService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class StudioServiceImpl implements StudioService{
    private final StudioRepository studioRepository;
    private final CloudinaryService cloudinaryService;

    private final String STUDIO_PROFILE_PICTURE_URL = "https://res.cloudinary.com/dfths157i/image/upload/v1729199808/directors/default_pfp.jpg";
    private final String FOLDER_NAME = "studios";

    private String getDefaultPictureUrl(){
        return  STUDIO_PROFILE_PICTURE_URL;
    }
    private String getDefaultFolderName(){
        return FOLDER_NAME;
    }

    public StudioServiceImpl(StudioRepository studioRepository, CloudinaryService cloudinaryService) {
        this.studioRepository = studioRepository;
        this.cloudinaryService = cloudinaryService;
    }

    @Override
    public StudioResponseDTO getStudioById(UUID id) {
        Studio studio = studioRepository.findById(id).orElseThrow(()->new RuntimeException("Studio not found."));
        return StudioMapper.studioResponseDTO(studio);
    }

    @Override
    public List<StudioResponseDTO> getAllStudio() {
        List<Studio> studios = studioRepository.findAll();
        List<StudioResponseDTO> result= new ArrayList<>();
        for(Studio studio:studios){
            result.add(StudioMapper.studioResponseDTO(studio));
        }
        return result;
    }

    @Override
    public StudioResponseDTO createStudio(StudioRequestDTO studioRequestDTO) {
        Studio studio = StudioMapper.toEntity(studioRequestDTO);
        String studioPictureUrl=uploadFile(studioRequestDTO.getFile(),studioRequestDTO.getPublicId());
        studio.setStudioProfileUrl(studioPictureUrl);
        Studio savedStudio = studioRepository.save(studio);
        return StudioMapper.studioResponseDTO(savedStudio);
    }

    @Override
    public StudioResponseDTO updateStudioById(StudioRequestDTO studioRequestDTO, UUID id) {
        Studio studio = studioRepository.findById(id).orElseThrow(()->new RuntimeException("Studio doesn't exist."));
        studio.setDescription(studioRequestDTO.getDescription());
        String studioPictureUrl = studio.getStudioProfileUrl();
        if(studioRequestDTO.getFile()!=null){
            deleteFile(studio.getPublicId());
            studioPictureUrl =  uploadFile(studioRequestDTO.getFile(),studioRequestDTO.getPublicId());
        }
        studio.setName(studioRequestDTO.getName());
        studio.setStudioProfileUrl(studioPictureUrl);
        Studio updatedStudio = studioRepository.save(studio);
        return StudioMapper.studioResponseDTO(updatedStudio);
    }

    @Override
    public void deleteStudioById(UUID id) {
        Studio studio = studioRepository.findById(id).orElseThrow(()->new RuntimeException("Studio not found."));
        studioRepository.deleteById(studio.getId());
    }

    @Override
    public String uploadFile(MultipartFile file, String publicId) {
        if(file!=null){
            try{
                return cloudinaryService.uploadFile(file,getDefaultFolderName(),publicId);
            }catch (IOException e){
                System.out.println("Problem in uploading studio picture on createStudio method");
            }
        }
        return getDefaultPictureUrl();
    }

    @Override
    public void deleteFile(String publicId){
       try {
           cloudinaryService.deleteFile(publicId);
       }catch (IOException e){
           System.out.println("Problem in uploading studio picture on createStudio method");
       }
    }


}
