package com.localcinema.moviecatalog.service;

import com.localcinema.moviecatalog.dto.MovieSearchResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.beans.factory.annotation.Value;

@Service
public class TmdbClient {
    private final RestClient restClient;
    private final String apiKey;

    public TmdbClient(
            @Value("${tmdb.base-url}") String baseUrl,
            @Value("${tmdb.api.key}") String apiKey) {

        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .build();

        this.apiKey = apiKey;
    }

    public MovieSearchResponse searchMovie(String title) {
        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search/movie")
                        .queryParam("query", title)
                        .queryParam("language", "en-US")
                        .queryParam("api_key", apiKey)
                        .build())
                .retrieve()
                .body(MovieSearchResponse.class);
    }
    

}
