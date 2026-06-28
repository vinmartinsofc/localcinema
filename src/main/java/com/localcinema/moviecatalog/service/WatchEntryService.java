package com.localcinema.moviecatalog.service;

import com.localcinema.moviecatalog.model.TitleType;
import com.localcinema.moviecatalog.model.WatchEntry;
import com.localcinema.moviecatalog.repository.WatchEntryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class WatchEntryService {

    private final WatchEntryRepository watchEntryRepository;

    public WatchEntryService(WatchEntryRepository watchEntryRepository) {
        this.watchEntryRepository = watchEntryRepository;
    }

    /** Registra uma nova sessão de visualização para um título (filme ou série) */
    public WatchEntry registerWatch(TitleType titleType, long titleId, LocalDate watchedAt, String comment) {
        WatchEntry entry = new WatchEntry(titleType, titleId, watchedAt, comment);
        return watchEntryRepository.save(entry);
    }

    /** Todas as sessões de visualização de um título específico, mais recente primeiro */
    public List<WatchEntry> getHistory(TitleType titleType, long titleId) {
        return watchEntryRepository.findByTitleTypeAndTitleIdOrderByWatchedAtDesc(titleType, titleId);
    }

    /** Quantidade de vezes que um título foi assistido */
    public long countWatches(TitleType titleType, long titleId) {
        return watchEntryRepository.countByTitleTypeAndTitleId(titleType, titleId);
    }

    /** Todas as sessões de visualização de séries (para o menu "listar séries assistidas") */
    public List<WatchEntry> listAllSeriesWatches() {
        return watchEntryRepository.findByTitleTypeOrderByWatchedAtDesc(TitleType.SERIES);
    }
}