package com.localcinema.moviecatalog.repository;

import com.localcinema.moviecatalog.model.Series;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeriesRepository extends JpaRepository<Series, Long> {
}