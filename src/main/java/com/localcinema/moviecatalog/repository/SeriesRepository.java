package com.localcinema.moviecatalog.repository;

import com.localcinema.moviecatalog.model.Series;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SeriesRepository extends JpaRepository<Series, Long> {

    Optional<Series> findByTmdbId(Long tmdbId);

    List<Series> findByNameContainingIgnoreCase(String name);
}