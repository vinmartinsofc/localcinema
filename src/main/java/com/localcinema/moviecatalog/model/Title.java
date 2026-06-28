package com.localcinema.moviecatalog.model;

import jakarta.persistence.MappedSuperclass;

import java.time.LocalDate;

@MappedSuperclass
public abstract class Title {

    private String name;
    private LocalDate releaseDate;
    private Integer duration;
    private String director;

    public Title() {
    }

    public Title(String name, LocalDate releaseDate, Integer duration, String director) {
        this.name = name;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.director = director;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    @Override
    public String toString() {
        return "Title{" +
                "name='" + name + '\'' +
                ", releaseDate=" + releaseDate +
                ", duration=" + duration +
                ", director='" + director + '\'' +
                '}';
    }
}