package com.pradiptakalita.dto.director;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Locale;
import java.util.UUID;

@Getter
@Setter
public class DirectorRequestDTO {
    // for updates
    private UUID id;

    @NotBlank(message = "Please enter name of the director.")
    @Size(min = 1, max = 255, message = "Name must be between 2 and 255 characters.")
    private String name;

    @NotBlank(message = "Please write something about the director.")
    @Size(min = 5,max = 65535, message = "Mini biography must be between 500 and 65535 characters.")
    private String miniBiography;

    private String profilePictureUrl;

    @Past(message = "Birth date must be in the past.")
    private LocalDate birthDate;

    private MultipartFile file;

    public String getPublicId() {
        return this.name.replaceAll("[^a-zA-Z0-9]", "")
                .replace(" ", "")
                .toLowerCase(Locale.ROOT)
                .strip();
    }
}
