package com.pradiptakalita.entity;

import com.pradiptakalita.validation.ValidReleaseYear;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "Please enter a movie title.")
    @Size(min = 1,max = 255, message = "Title must be between 1 and 255 characters.")
    @Column(nullable = false)
    private String title;

    // Many-to-One relation with Studio Entity
    @ManyToOne
    @JoinColumn(name = "studio_id",nullable = false)
    private Studio studio;

    // Many-to-Many relation with Director Entity
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(
            name = "director_movie",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "director_id")
    )
    private Set<Director> directors = new HashSet<>();

    // Many-to-Many relation with Actor Entity
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(
            name = "actor_movie",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
    private Set<Actor> actors = new HashSet<>();

    @NotNull(message = "Please enter the release year of the movie.")
    @ValidReleaseYear
    private Integer releaseYear;

    @NotBlank(message = "Movie poster is required.")
    @Column(nullable = false,length = 2000)
    @Size(min = 1, max = 2000, message = "The movie poster url must be between 1 and 2000 characters.")
    private String poster;
}
