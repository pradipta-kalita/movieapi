package com.pradiptakalita.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.engine.internal.Cascade;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Table(name = "directors")
public class Director {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "Please enter name of the director.")
    @Size(min = 1, max = 255, message = "Name must be between 2 and 255 characters.")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Please write something about the director.")
    @Size(min = 5,max = 65535, message = "Mini biography must be between 500 and 65535 characters.")
    @Column(columnDefinition = "TEXT",name = "mini_biography")
    private String miniBiography;

    @Past(message = "Birth date must be in the past.")
    @Column(name = "dob")
    private LocalDate birthDate;

    @NotBlank(message = "Profile picture is required.")
    @Column(nullable = false,length = 2000,name = "profile_picture_url")
    @Size(min = 1, max = 2000, message = "The profile picture url must be between 1 and 2000 characters.")
    private String profilePictureUrl;

    // Many-to-Many relation with Movie Entity
    @ManyToMany(mappedBy = "directors")
    private Set<Movie> movies = new HashSet<>();

    public String getPublicId(){
        return name.toLowerCase(Locale.ROOT).strip().replace(" ","");
    }
}
