package com.localcinema.moviecatalog.service;

import com.localcinema.moviecatalog.dto.MovieDto;
import com.localcinema.moviecatalog.dto.MovieSearchResponse;
import com.localcinema.moviecatalog.model.Movie;
import com.localcinema.moviecatalog.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class MovieService {

    private final TmdbClient tmdbClient;
    private final MovieRepository movieRepository;

    public MovieService(TmdbClient tmdbClient, MovieRepository movieRepository) {
        this.tmdbClient = tmdbClient;
        this.movieRepository = movieRepository;
    }

    /** Busca direto na TMDB, sem tocar no banco local */
    public MovieSearchResponse searchOnTmdb(String title) {
        return tmdbClient.searchMovie(title);
    }

    /** Busca no banco local (filmes já salvos anteriormente) */
    public List<Movie> searchLocal(String title) {
        return movieRepository.findByNameContainingIgnoreCase(title);
    }

    /** Busca na TMDB e salva (ou atualiza) os resultados no banco local */
    public List<Movie> searchAndCache(String title) {
        MovieSearchResponse response = tmdbClient.searchMovie(title);

        return response.results().stream()
                .map(this::saveOrUpdate)
                .toList();
    }

    private Movie saveOrUpdate(MovieDto dto) {
        Movie movie = movieRepository.findByTmdbId(dto.id())
                .orElseGet(Movie::new);

        movie.setTmdbId(dto.id());
        movie.setName(dto.title());
        movie.setReleaseDate(parseDate(dto.releaseDate()));
        movie.setOverview(dto.overview());
        movie.setPosterPath(dto.posterPath());
        movie.setVoteAverage(dto.voteAverage());

        return movieRepository.save(movie);
    }

    private LocalDate parseDate(String date) {
        if (date == null || date.isBlank()) {
            return null;
        }
        return LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
    }
}