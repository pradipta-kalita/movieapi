package com.pradiptakalita.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Table(name = "actors")
public class Actor {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "Please enter name of the actor/actress.")
    @Size(min = 1, max = 255, message = "Name must be between 2 and 255 characters.")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Please write something about the actor/actress.")
    @Size(min = 10,max = 65535, message = "Mini biography must be between 500 and 65535 characters.")
    @Column(columnDefinition = "TEXT",name = "mini_biography")
    private String miniBiography;

    @Past(message = "Birth date must be in the past.")
    @Column(name = "dob")
    private LocalDate birthDate;

    @NotBlank(message = "Profile picture is required.")
    @Column(nullable = false,length = 2000,name = "profile_picture_url")
    @Size(min = 1, max = 2000, message = "The profile picture url must be between 1 and 2000 characters.")
    private String profilePictureUrl;

    @ManyToMany(mappedBy = "actors")
    @JsonBackReference
    @JsonManagedReference
    private Set<Movie> movies= new HashSet<>();

    public String getPublicId(){
        return name.replace(" ","").toLowerCase(Locale.ROOT).strip();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Actor)) return false;
        Actor actor = (Actor) o;
        return id != null && id.equals(actor.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
