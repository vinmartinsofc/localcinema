package com.localcinema.moviecatalog.dto;

import java.util.List;

public record MovieSearchResponse(
        List<MovieDto> results
) {
}