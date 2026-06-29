package com.localcinema.moviecatalog.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SeriesDto(

        long id,
        String name,

        @JsonProperty("first_air_date")
        String firstAirDate,

        String overview,

        @JsonProperty("poster_path")
        String posterPath,

        @JsonProperty("vote_average")
        Double voteAverage

) {
}

