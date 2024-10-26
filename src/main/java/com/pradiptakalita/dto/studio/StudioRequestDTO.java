package com.pradiptakalita.dto.studio;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Locale;

@Data
public class StudioRequestDTO {
    @NotBlank(message = "Please enter name of the studio.")
    @Size(min = 1,max = 255, message = "Studio name must be between 2 and 255 characters.")
    private String name;

    @NotBlank(message = "Please write something about the studio.")
    private String description;

    private MultipartFile file;

    public String getPublicId() {
        return this.name.replaceAll("[^a-zA-Z0-9]", "")
                .replace(" ", "")
                .toLowerCase(Locale.ROOT)
                .strip();
    }
}
