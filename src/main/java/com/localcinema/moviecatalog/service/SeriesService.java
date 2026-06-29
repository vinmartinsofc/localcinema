package com.localcinema.moviecatalog.service;

import com.localcinema.moviecatalog.dto.SeriesDto;
import com.localcinema.moviecatalog.dto.SeriesSearchResponse;
import com.localcinema.moviecatalog.model.Series;
import com.localcinema.moviecatalog.repository.SeriesRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class SeriesService {

    private final TmdbClient tmdbClient;
    private final SeriesRepository seriesRepository;

    public SeriesService(TmdbClient tmdbClient, SeriesRepository seriesRepository) {
        this.tmdbClient = tmdbClient;
        this.seriesRepository = seriesRepository;
    }

    /** Busca direto na TMDB, sem tocar no banco local */
    public SeriesSearchResponse searchOnTmdb(String title) {
        return tmdbClient.searchSeries(title);
    }

    /** Busca no banco local (séries já salvas anteriormente) */
    public List<Series> searchLocal(String title) {
        return seriesRepository.findByNameContainingIgnoreCase(title);
    }

    /** Busca na TMDB e salva (ou atualiza) os resultados no banco local */
    public List<Series> searchAndCache(String title) {
        SeriesSearchResponse response = tmdbClient.searchSeries(title);

        return response.results().stream()
                .map(this::saveOrUpdate)
                .toList();
    }

    private Series saveOrUpdate(SeriesDto dto) {
        Series series = seriesRepository.findByTmdbId(dto.id())
                .orElseGet(Series::new);

        series.setTmdbId(dto.id());
        series.setName(dto.name());
        series.setReleaseDate(parseDate(dto.firstAirDate()));
        series.setOverview(dto.overview());
        series.setPosterPath(dto.posterPath());
        series.setVoteAverage(dto.voteAverage());

        return seriesRepository.save(series);
    }

    private LocalDate parseDate(String date) {
        if (date == null || date.isBlank()) {
            return null;
        }
        return LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public List<Series> listAllLocal() {
        return seriesRepository.findAll();
    }
}