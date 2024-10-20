package com.pradiptakalita.mapper;

import com.pradiptakalita.dto.actor.ActorResponseDTO;
import com.pradiptakalita.dto.director.DirectorResponseDTO;
import com.pradiptakalita.dto.movie.MovieRequestDTO;
import com.pradiptakalita.dto.movie.MovieResponseDTO;
import com.pradiptakalita.entity.Actor;
import com.pradiptakalita.entity.Director;
import com.pradiptakalita.entity.Movie;

import java.util.HashSet;
import java.util.Set;

public class MovieMapper {
    public static Movie toEntity(MovieRequestDTO movieRequestDTO){
        Movie movie = new Movie();
        movie.setTitle(movieRequestDTO.getTitle());
        movie.setReleaseYear(movieRequestDTO.getReleaseYear());
        return movie;
    }

    public static MovieResponseDTO toResponseDTO(Movie movie){
        MovieResponseDTO movieResponseDTO = new MovieResponseDTO();
        movieResponseDTO.setId(movie.getId());
        movieResponseDTO.setTitle(movie.getTitle());
        movieResponseDTO.setReleaseYear(movie.getReleaseYear());
        movieResponseDTO.setStudio(movie.getStudio());
        movieResponseDTO.setPosterUrl(movie.getMoviePosterUrl());
        Set<DirectorResponseDTO> directors = new HashSet<>();
        for(Director director:movie.getDirectors()){
            directors.add(DirectorMapper.toResponseDTO(director));
        }
        movieResponseDTO.setDirectors(directors);

        Set<ActorResponseDTO> actors = new HashSet<>();
        for(Actor actor:movie.getActors()){
            actors.add(ActorMapper.toResponseDTO(actor));
        }
        movieResponseDTO.setActors(actors);

        return movieResponseDTO;
    }
}