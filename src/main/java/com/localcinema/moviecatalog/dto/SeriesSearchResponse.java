
package com.localcinema.moviecatalog.dto;

import java.util.List;

public record SeriesSearchResponse(
        List<SeriesDto> results
) {
}