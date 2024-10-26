package com.pradiptakalita.service.cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
@Transactional
public class CloudinaryServiceImpl implements CloudinaryService {

    private final Cloudinary cloudinary;

    public CloudinaryServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public String uploadFileToCloudinary(MultipartFile file, String folderName, String fileName) throws IOException {
        Map<String, Object> options = new HashMap<>();
        options.put("public_id", fileName);
        options.put("folder", folderName);
        options.put("resource_type", "image");

        options.put("transformation", new Transformation<>().width(300)  // Set the desired width
                .height(300)  // Set the desired height
                .crop("thumb")  // Crop type 'thumb' for best fit
                .gravity("face")  // Focus on face
                .radius("max"));  // Makes the image circular


        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), options);
        return uploadResult.get("url").toString();
    }

    @Override
    public void deleteFileFromCloudinary(String publicId,String folderName) throws IOException {
        // Destroy (delete) the image by its public ID
        Map result = cloudinary.uploader().destroy(folderName+"/"+publicId, ObjectUtils.emptyMap());
        System.out.println(result.get("result"));
        if ("ok".equals(result.get("result"))) {
            System.out.println("File deleted successfully");
        } else {
            System.out.println("File deletion failed: "+publicId);
        }
    }

    @Override
    public String uploadFile(MultipartFile file,String folderName, String publicId,String defaultURL) {
        if(file!=null){
            try{
                return uploadFileToCloudinary(file,folderName,publicId);
            }catch (IOException e){
                System.out.println("Problem in uploading studio picture on createStudio method");
                return defaultURL;
            }
        }
        return defaultURL;
    }

    @Override
    public void deleteFile(String publicId,String folderName){
        try {
            deleteFileFromCloudinary(publicId,folderName);
        }catch (IOException e){
            System.out.println("Problem in uploading studio picture on createStudio method");
        }
    }

    @Override
    public String uploadMoviePoster(MultipartFile file, String folderName, String publicId, String defaultURL) {
        if (file != null && !file.isEmpty()) {
            try {
                Map<String, Object> options = new HashMap<>();
                options.put("public_id", publicId);
                options.put("folder", folderName);
                options.put("resource_type", "image");
                options.put("transformation", new Transformation<>()
                        .width(1800)
                        .height(1200)
                        .crop("fill")
                        .gravity("center")
                        .quality("auto"));

                // Use InputStream for upload
                Map uploadResult = cloudinary.uploader().upload(file.getInputStream(), options);
                return uploadResult.get("url").toString();

            } catch (IOException e) {
                System.out.println("Problem in uploading movie poster: " + e.getMessage());
                return defaultURL;
            }
        }
        return defaultURL;
    }



}
