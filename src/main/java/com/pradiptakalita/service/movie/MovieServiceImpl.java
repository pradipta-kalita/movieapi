package com.pradiptakalita.service.movie;

import com.pradiptakalita.dto.movie.MoviePageResponseDTO;
import com.pradiptakalita.dto.movie.MovieRequestDTO;
import com.pradiptakalita.dto.movie.MovieResponseDTO;
import com.pradiptakalita.entity.Actor;
import com.pradiptakalita.entity.Director;
import com.pradiptakalita.entity.Movie;
import com.pradiptakalita.entity.Studio;
import com.pradiptakalita.exceptions.EntityNotFoundException;
import com.pradiptakalita.mapper.MovieMapper;
import com.pradiptakalita.repository.ActorRepository;
import com.pradiptakalita.repository.DirectorRepository;
import com.pradiptakalita.repository.MovieRepository;
import com.pradiptakalita.repository.StudioRepository;
import com.pradiptakalita.service.cloudinary.CloudinaryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public MovieServiceImpl(MovieRepository movieRepository,
                            CloudinaryService cloudinaryService,
                            StudioRepository studioRepository,
                            ActorRepository actorRepository,
                            DirectorRepository directorRepository) {
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
        return MovieMapper.toResponseDTO(movieRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Movie not found with id : "+id)));
    }

    @Override
    public MoviePageResponseDTO getAllMovies(int page, int size, String sortBy,String order) {
        String[] sortFields = sortBy.split(",");
        Sort sort = Sort.by(order.equalsIgnoreCase("asc") ? Sort.Order.asc(sortFields[0]) : Sort.Order.desc(sortFields[0]));
        for (int i = 1; i < sortFields.length; i++) {
            sort = sort.and(Sort.by(order.equalsIgnoreCase("asc") ? Sort.Order.asc(sortFields[i]) : Sort.Order.desc(sortFields[i])));
        }
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Movie> moviePage = movieRepository.findAll(pageable);
        List<MovieResponseDTO> movieResponseDTOS= moviePage.getContent().stream().map(MovieMapper::toResponseDTO).toList();
        return new MoviePageResponseDTO(
                movieResponseDTOS,
                moviePage.getTotalElements(),
                moviePage.getTotalPages(),
                moviePage.getNumber(),
                moviePage.getSize(),
                moviePage.hasNext(),
                moviePage.hasPrevious()
        );
    }

    @Override
    @Transactional
    public MovieResponseDTO createMovie(MovieRequestDTO movieRequestDTO) {
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

            director.getMovies().add(movie);
        }
        movie.setDirectors(directors);

        Set<Actor> actors = new HashSet<>();
        for (UUID actorId : movieRequestDTO.getActorIds()) {
            Actor actor = actorRepository.findById(actorId)
                    .orElseThrow(() -> new RuntimeException("Actor not found."));
            actors.add(actor);

            actor.getMovies().add(movie);
        }
        movie.setActors(actors);

        String moviePosterUrl = cloudinaryService.uploadMoviePoster(movieRequestDTO.getFile(),
                getDefaultFolderName(), movieRequestDTO.getPublicId(), getDefaultPictureUrl());
        movie.setMoviePosterUrl(moviePosterUrl);

        Movie savedMovie = movieRepository.save(movie);

        return MovieMapper.toResponseDTO(savedMovie);
    }


    @Override
    public MovieResponseDTO updateMovieById(MovieRequestDTO movieRequestDTO, UUID id) {
        Movie movie = movieRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Movie not found with id: "+id));
        movie.setTitle(movieRequestDTO.getTitle());
        movie.setReleaseYear(movieRequestDTO.getReleaseYear());

        Set<Director> directors = new HashSet<>();
        for(UUID directorId: movieRequestDTO.getDirectorIds()){
            Director director = directorRepository.findById(directorId).orElseThrow(()->new EntityNotFoundException("Director not found with id: "+directorId ));
            directors.add(director);
        }
        movie.setDirectors(directors);


        Set<Actor> actors = new HashSet<>();
        for(UUID actorId: movieRequestDTO.getActorIds()){
            Actor actor = actorRepository.
                    findById(actorId).
                    orElseThrow(()->
                            new EntityNotFoundException("Actor not found with id: "+actorId));
            actors.add(actor);
        }
        movie.setActors(actors);

        Studio studio = studioRepository.
                findById(movieRequestDTO.getStudioId()).
                orElseThrow(()->
                        new EntityNotFoundException("Studio not found with id :"+movieRequestDTO.getStudioId()));
        movie.setStudio(studio);

        if(movieRequestDTO.getFile()!=null){
            String moviePosterUrl = cloudinaryService.uploadMoviePoster(movieRequestDTO.getFile(),getDefaultFolderName(),movieRequestDTO.getPublicId(),getDefaultPictureUrl());
            movie.setMoviePosterUrl(moviePosterUrl);
        }
        Movie updatedMovie = movieRepository.save(movie);
        return MovieMapper.toResponseDTO(updatedMovie);
    }

    @Override
    public String deleteMovieById(UUID id) {
        Movie movie = movieRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Movie not found with id : "+id));
        movieRepository.deleteById(id);
        return "Movie successfully deleted.";
    }
}
