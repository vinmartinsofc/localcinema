package com.localcinema.moviecatalog.repository;

import com.localcinema.moviecatalog.model.TitleType;
import com.localcinema.moviecatalog.model.WatchEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WatchEntryRepository extends JpaRepository<WatchEntry, Long> {

    List<WatchEntry> findByTitleTypeAndTitleIdOrderByWatchedAtDesc(TitleType titleType, long titleId);

    long countByTitleTypeAndTitleId(TitleType titleType, long titleId);

    List<WatchEntry> findByTitleTypeOrderByWatchedAtDesc(TitleType titleType);
}