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

    private String creator;
    private Integer episodes;
    private boolean finished;

    public Series() {
    }

    public Series(String name, LocalDate releaseDate, Integer duration, String director,
                  String creator, Integer episodes, boolean finished) {
        super(name, releaseDate, duration, director);
        this.creator = creator;
        this.episodes = episodes;
        this.finished = finished;
    }

    public long getId() {
        return id;
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
                ", name='" + getName() + '\'' +
                ", creator='" + creator + '\'' +
                ", episodes=" + episodes +
                ", finished=" + finished +
                '}';
    }
}