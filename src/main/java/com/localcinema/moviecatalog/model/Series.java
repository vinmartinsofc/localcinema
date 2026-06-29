package com.localcinema.moviecatalog.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "series")
public class Series extends Title {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Long tmdbId;

    private String overview;
    private String posterPath;
    private Double voteAverage;

    private String creator;
    private Integer episodes;
    private boolean finished;

    public Series() {
    }

    public Series(String name, LocalDate releaseDate, Integer duration, String director,
                  Long tmdbId, String overview, String posterPath, Double voteAverage,
                  String creator, Integer episodes, boolean finished) {
        super(name, releaseDate, duration, director);
        this.tmdbId = tmdbId;
        this.overview = overview;
        this.posterPath = posterPath;
        this.voteAverage = voteAverage;
        this.creator = creator;
        this.episodes = episodes;
        this.finished = finished;
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

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Integer getEpisodes() {
        return episodes;
    }

    public void setEpisodes(Integer episodes) {
        this.episodes = episodes;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    @Override
    public String toString() {
        return "Series{" +
                "id=" + id +
                ", tmdbId=" + tmdbId +
                ", name='" + getName() + '\'' +
                ", creator='" + creator + '\'' +
                ", episodes=" + episodes +
                ", finished=" + finished +
                '}';
    }
}