package com.localcinema.moviecatalog.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "movies")
public class Movie extends Title {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /** ID do filme na TMDB, para evitar duplicar no cache local */
    private Long tmdbId;

    private String overview;
    private String posterPath;
    private Double voteAverage;

    public Movie() {
    }

    public Movie(String name, LocalDate releaseDate, Integer duration, String director,
                 Long tmdbId, String overview, String posterPath, Double voteAverage) {
        super(name, releaseDate, duration, director);
        this.tmdbId = tmdbId;
        this.overview = overview;
        this.posterPath = posterPath;
        this.voteAverage = voteAverage;
    }

    public long getId() {
        return id;
    }

    public Long getTmdbId() {
        return tmdbId;
    }

    public void setTmdbId(Long tmdbId) {
        this.tmdbId = tmdbId;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", tmdbId=" + tmdbId +
                ", name='" + getName() + '\'' +
                ", overview='" + overview + '\'' +
                '}';
    }
}