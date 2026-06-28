package com.localcinema.moviecatalog.controller;

import com.localcinema.moviecatalog.dto.MovieSearchResponse;
import com.localcinema.moviecatalog.model.Movie;
import com.localcinema.moviecatalog.service.MovieService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService service;

    public MovieController(MovieService service) {
        this.service = service;
    }

    /** GET /movies/search/tmdb?title=matrix -> busca direto na API da TMDB */
    @GetMapping("/search/tmdb")
    public MovieSearchResponse searchOnTmdb(@RequestParam String title) {
        return service.searchOnTmdb(title);
    }

    /** GET /movies/search/local?title=matrix -> busca no banco local */
    @GetMapping("/search/local")
    public List<Movie> searchLocal(@RequestParam String title) {
        return service.searchLocal(title);
    }

    /** POST /movies/search/cache?title=matrix -> busca na TMDB e salva no banco */
    @PostMapping("/search/cache")
    public List<Movie> searchAndCache(@RequestParam String title) {
        return service.searchAndCache(title);
    }
}