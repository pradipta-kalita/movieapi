package com.pradiptakalita.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Table(name = "studios")
public class Studio {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "Please enter name of the studio.")
    @Size(min = 1,max = 255, message = "Studio name must be between 2 and 255 characters.")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Please write something about the studio.")
    @Column(columnDefinition = "TEXT")
    private String description;

    private String studioProfileUrl;
    // One-to-Many relation with Movie Entity
    @OneToMany(mappedBy = "studio",cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private Set<Movie> movies = new HashSet<>();

    public String getPublicId(){
        return name.toLowerCase(Locale.ROOT).strip().replace(" ","");
    }
}
