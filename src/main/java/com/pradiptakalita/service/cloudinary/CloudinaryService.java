package com.pradiptakalita.service.cloudinary;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CloudinaryService {
    String uploadFileToCloudinary(MultipartFile file, String folderName, String fileName) throws IOException;
    void deleteFileFromCloudinary(String publicId,String folderName) throws IOException;
    String uploadFile(MultipartFile file,String folderName, String publicId,String defaultURL);
    void deleteFile(String publicId,String folderName);
    String uploadMoviePoster(MultipartFile file, String folderName, String publicId, String defaultURL);
}
