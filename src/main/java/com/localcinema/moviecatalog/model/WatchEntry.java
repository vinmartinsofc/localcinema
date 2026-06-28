package com.localcinema.moviecatalog.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "watch_entries")
public class WatchEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private TitleType titleType;

    private long titleId;

    private LocalDate watchedAt;

    private String comment;

    public WatchEntry() {
    }

    public WatchEntry(TitleType titleType, long titleId, LocalDate watchedAt, String comment) {
        this.titleType = titleType;
        this.titleId = titleId;
        this.watchedAt = watchedAt;
        this.comment = comment;
    }

    public long getId() {
        return id;
    }

    public TitleType getTitleType() {
        return titleType;
    }

    public void setTitleType(TitleType titleType) {
        this.titleType = titleType;
    }

    public long getTitleId() {
        return titleId;
    }

    public void setTitleId(long titleId) {
        this.titleId = titleId;
    }

    public LocalDate getWatchedAt() {
        return watchedAt;
    }

    public void setWatchedAt(LocalDate watchedAt) {
        this.watchedAt = watchedAt;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "WatchEntry{" +
                "id=" + id +
                ", titleType=" + titleType +
                ", titleId=" + titleId +
                ", watchedAt=" + watchedAt +
                ", comment='" + comment + '\'' +
                '}';
    }
}