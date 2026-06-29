package com.localcinema.moviecatalog.controller;

import com.localcinema.moviecatalog.dto.SeriesSearchResponse;
import com.localcinema.moviecatalog.model.Series;
import com.localcinema.moviecatalog.service.SeriesService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/series")
public class SeriesController {

    private final SeriesService service;

    public SeriesController(SeriesService service) {
        this.service = service;
    }

    /** GET /series/search/tmdb?title=breaking+bad -> busca direto na API da TMDB */
    @GetMapping("/search/tmdb")
    public SeriesSearchResponse searchOnTmdb(@RequestParam String title) {
        return service.searchOnTmdb(title);
    }

    /** GET /series/search/local?title=breaking+bad -> busca no banco local */
    @GetMapping("/search/local")
    public List<Series> searchLocal(@RequestParam String title) {
        return service.searchLocal(title);
    }

    /** POST /series/search/cache?title=breaking+bad -> busca na TMDB e salva no banco */
    @PostMapping("/search/cache")
    public List<Series> searchAndCache(@RequestParam String title) {
        return service.searchAndCache(title);
    }
}