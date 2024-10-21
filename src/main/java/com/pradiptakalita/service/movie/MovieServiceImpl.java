package com.pradiptakalita.service.movie;

import com.pradiptakalita.dto.movie.MovieRequestDTO;
import com.pradiptakalita.dto.movie.MovieResponseDTO;
import com.pradiptakalita.dto.studio.StudioResponseDTO;
import com.pradiptakalita.entity.Actor;
import com.pradiptakalita.entity.Director;
import com.pradiptakalita.entity.Movie;
import com.pradiptakalita.entity.Studio;
import com.pradiptakalita.mapper.MovieMapper;
import com.pradiptakalita.mapper.StudioMapper;
import com.pradiptakalita.repository.ActorRepository;
import com.pradiptakalita.repository.DirectorRepository;
import com.pradiptakalita.repository.MovieRepository;
import com.pradiptakalita.repository.StudioRepository;
import com.pradiptakalita.service.actor.ActorService;
import com.pradiptakalita.service.cloudinary.CloudinaryService;
import com.pradiptakalita.service.director.DirectorService;
import com.pradiptakalita.service.studio.StudioService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class MovieServiceImpl implements MovieService{

    private final MovieRepository movieRepository;
    private final CloudinaryService cloudinaryService;
    private final StudioRepository studioRepository;
    private final ActorRepository actorRepository;
    private final DirectorRepository directorRepository;

    public MovieServiceImpl(MovieRepository movieRepository, CloudinaryService cloudinaryService, StudioRepository studioRepository, ActorRepository actorRepository, DirectorRepository directorRepository) {
        this.movieRepository = movieRepository;
        this.cloudinaryService = cloudinaryService;
        this.studioRepository = studioRepository;
        this.actorRepository = actorRepository;
        this.directorRepository = directorRepository;
    }

    private final String DIRECTOR_POSTER_URL = "https://res.cloudinary.com/dfths157i/image/upload/v1729199808/directors/default_pfp.jpg";
    private final String FOLDER_NAME = "movies";

    private String getDefaultPictureUrl(){
        return DIRECTOR_POSTER_URL;
    }
    private String getDefaultFolderName(){
        return FOLDER_NAME;
    }

    @Override
    public MovieResponseDTO getMovieById(UUID id) {
        return MovieMapper.toResponseDTO(movieRepository.findById(id).orElseThrow(()->new RuntimeException("Movie not found.")));
    }

    @Override
    public List<MovieResponseDTO> getAllMovies() {
        List<Movie> movies = movieRepository.findAll();
        List<MovieResponseDTO> results= new ArrayList<>();
        for(Movie movie: movies){
            results.add(MovieMapper.toResponseDTO(movie));
        }
        return results;
    }

    @Override
    @Transactional
    public MovieResponseDTO createMovie(MovieRequestDTO movieRequestDTO) {
        System.out.println("SERVICE");
        Movie movie = MovieMapper.toEntity(movieRequestDTO);

        if (movieRequestDTO.getFile() == null) {
            throw new RuntimeException("Movie poster is required.");
        }

        Studio studio = studioRepository.findById(movieRequestDTO.getStudioId())
                .orElseThrow(() -> new RuntimeException("Studio not found."));
        movie.setStudio(studio);

        Set<Director> directors = new HashSet<>();
        for (UUID directorId : movieRequestDTO.getDirectorIds()) {
            Director director = directorRepository.findById(directorId)
                    .orElseThrow(() -> new RuntimeException("Director not found."));
            directors.add(director);
            // Add the movie to the director's collection
            director.getMovies().add(movie);
        }
        movie.setDirectors(directors);

        Set<Actor> actors = new HashSet<>();
        for (UUID actorId : movieRequestDTO.getActorIds()) {
            Actor actor = actorRepository.findById(actorId)
                    .orElseThrow(() -> new RuntimeException("Actor not found."));
            actors.add(actor);
            // Add the movie to the actor's collection
            actor.getMovies().add(movie);
        }
        movie.setActors(actors);

        String moviePosterUrl = cloudinaryService.uploadMoviePoster(movieRequestDTO.getFile(),
                getDefaultFolderName(), movieRequestDTO.getPublicId(), getDefaultPictureUrl());
        movie.setMoviePosterUrl(moviePosterUrl);

        // Save the movie
        Movie savedMovie = movieRepository.save(movie);

        // No need to save actors and directors explicitly again; cascade should handle it
        return MovieMapper.toResponseDTO(savedMovie);
    }


    @Override
    public MovieResponseDTO updateMovieById(MovieRequestDTO movieRequestDTO, UUID id) {
        return null;
    }

    @Override
    public String deleteMovieById(UUID id) {
        Movie movie = movieRepository.findById(id).orElseThrow(()-> new RuntimeException("Movie not found."));
        movieRepository.deleteById(id);
        return "Movie successfully deleted.";
    }
}
