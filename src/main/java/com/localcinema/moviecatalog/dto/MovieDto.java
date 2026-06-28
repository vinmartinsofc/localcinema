package com.localcinema.moviecatalog.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record MovieDto(

        long id,
        String title,

        @JsonProperty("release_date")
        String releaseDate,

        String overview,

        @JsonProperty("poster_path")
        String posterPath,

        @JsonProperty("vote_average")
        Double voteAverage

) {
}
