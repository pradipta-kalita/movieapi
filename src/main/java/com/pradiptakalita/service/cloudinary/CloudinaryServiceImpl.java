package com.pradiptakalita.service.cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {

    private final Cloudinary cloudinary;

    public CloudinaryServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public String uploadFile(MultipartFile file, String folderName, String fileName) throws IOException {
        System.out.println("YES CAME HERE, upload method");

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

}
