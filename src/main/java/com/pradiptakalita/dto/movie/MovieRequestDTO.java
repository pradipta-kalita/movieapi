package com.pradiptakalita.dto.movie;

import com.pradiptakalita.validation.ValidReleaseYear;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

@Data
public class MovieRequestDTO {
    @NotBlank(message = "Please enter a movie title.")
    @Size(min = 1,max = 255, message = "Title must be between 1 and 255 characters.")
    private String title;

    @NotNull(message = "Please enter the release year of the movie.")
    @ValidReleaseYear
    private Integer releaseYear;

    @NotNull(message = "Please provide a studio ID.")
    private UUID studioId;

    @NotEmpty(message = "The director list cannot be empty.")
    private Set<UUID> directorIds=new HashSet<>();

    @NotEmpty(message = "The actor list cannot be empty.")
    private Set<UUID> actorIds=new HashSet<>();

    private MultipartFile file;

    public String getPublicId(){
        return title.toLowerCase(Locale.ROOT).strip().replace(" ","");
    }
}
