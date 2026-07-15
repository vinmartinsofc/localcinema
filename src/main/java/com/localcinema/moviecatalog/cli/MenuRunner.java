package com.localcinema.moviecatalog.cli;

import com.localcinema.moviecatalog.dto.MovieDto;
import com.localcinema.moviecatalog.dto.MovieSearchResponse;
import com.localcinema.moviecatalog.dto.SeriesDto;
import com.localcinema.moviecatalog.dto.SeriesSearchResponse;
import com.localcinema.moviecatalog.model.Movie;
import com.localcinema.moviecatalog.model.Series;
import com.localcinema.moviecatalog.model.TitleType;
import com.localcinema.moviecatalog.model.WatchEntry;
import com.localcinema.moviecatalog.repository.SeriesRepository;
import com.localcinema.moviecatalog.service.MovieService;
import com.localcinema.moviecatalog.service.SeriesService;
import com.localcinema.moviecatalog.service.WatchEntryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

@Component
public class MenuRunner implements CommandLineRunner {

    private final MovieService movieService;
    private final SeriesService seriesService;
    private final WatchEntryService watchEntryService;
    private final SeriesRepository seriesRepository;
    private final Scanner scanner = new Scanner(System.in);

    public MenuRunner(MovieService movieService, SeriesService seriesService,
                      WatchEntryService watchEntryService, SeriesRepository seriesRepository) {
        this.movieService = movieService;
        this.seriesService = seriesService;
        this.watchEntryService = watchEntryService;
        this.seriesRepository = seriesRepository;
    }


    @Override
    public void run(String... args) {
        Thread menuThread = new Thread(this::startMenu, "cli-menu");
        menuThread.setDaemon(false);
        menuThread.start();
    }

    private void startMenu() {
        boolean running = true;

        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();


            switch (choice) {
                case "1" -> searchOnTmdb();
                case "2" -> searchAndCache();
                case "3" -> searchLocal();
                case "4" -> listAllLocal();
                case "5" -> searchSeriesOnTmdb();
                case "6" -> searchAndCacheSeries();
                case "7" -> searchLocalSeries();
                case "8" -> listAllLocalSeries();
                case "9" -> registerWatch();
                case "10" -> listWatchedSeries();
                case "0" -> {
                    System.out.println("Saindo do menu... (servidor web continua ativo)");
                    running = false;
                }
                default -> System.out.println("Opção inválida. Tente novamente.\n");
            }
        }
    }

    private void printMenu() {
        System.out.println("""
            ====================================
              LOCAL CINEMA - MENU
            ====================================
            1. Buscar filme na TMDB (sem salvar)
            2. Buscar na TMDB e salvar/cachear
            3. Buscar filme salvo localmente
            4. Listar todos os filmes salvos
            5. Buscar série na TMDB (sem salvar)
            6. Buscar série na TMDB e salvar/cachear
            7. Buscar série salva localmente
            8. Listar todas as séries salvas
            9. Registrar visualização (filme ou série)
            10. Listar séries assistidas (qtd, data, comentário)
            0. Sair do menu
            ====================================
            Escolha uma opção:""");
    }

    private String askTitle() {
        System.out.print("Digite o título do filme: ");
        return scanner.nextLine().trim();
    }

    private void searchOnTmdb() {
        String title = askTitle();
        try {
            MovieSearchResponse response = movieService.searchOnTmdb(title);
            List<MovieDto> results = response.results();

            if (results.isEmpty()) {
                System.out.println("Nenhum resultado encontrado na TMDB.\n");
                return;
            }


            System.out.println("\n--- Resultados da TMDB ---");
            results.forEach(dto -> System.out.printf(
                    "- %s (%s) | nota: %s%n",
                    dto.title(), dto.releaseDate(), dto.voteAverage()));
            System.out.println();

        } catch (Exception e) {
            System.out.println("Erro ao buscar na TMDB: " + e.getMessage() + "\n");
        }
    }


    private void searchSeriesOnTmdb() {
        String title = askTitle();
        try {
            SeriesSearchResponse response = seriesService.searchOnTmdb(title);
            List<SeriesDto> results = response.results();

            if (results.isEmpty()) {
                System.out.println("Nenhum resultado encontrado na TMDB.\n");
                return;
            }

            System.out.println("\n--- Resultados da TMDB (séries) ---");
            results.forEach(dto -> System.out.printf(
                    "- %s (%s) | nota: %s%n",
                    dto.name(), dto.firstAirDate(), dto.voteAverage()));
            System.out.println();

        } catch (Exception e) {
            System.out.println("Erro ao buscar na TMDB: " + e.getMessage() + "\n");
        }
    }

    private void searchAndCacheSeries() {
        String title = askTitle();
        try {
            List<Series> saved = seriesService.searchAndCache(title);

            if (saved.isEmpty()) {
                System.out.println("Nenhum resultado encontrado para salvar.\n");
                return;
            }

            System.out.println("\n--- Séries salvas/atualizadas no banco ---");
            saved.forEach(series -> System.out.printf(
                    "- [id=%d] %s (%s)%n",
                    series.getId(), series.getName(), series.getReleaseDate()));
            System.out.println();

        } catch (Exception e) {
            System.out.println("Erro ao buscar/salvar: " + e.getMessage() + "\n");
        }
    }

    private void searchLocalSeries() {
        String title = askTitle();
        List<Series> results = seriesService.searchLocal(title);

        if (results.isEmpty()) {
            System.out.println("Nenhuma série encontrada no banco local.\n");
            return;
        }

        System.out.println("\n--- Séries no banco local ---");
        results.forEach(series -> System.out.printf(
                "- [id=%d] %s (%s)%n",
                series.getId(), series.getName(), series.getReleaseDate()));
        System.out.println();
    }

    private void listAllLocalSeries() {
        List<Series> all = seriesService.listAllLocal();

        if (all.isEmpty()) {
            System.out.println("Nenhuma série salva no banco ainda.\n");
            return;
        }

        System.out.println("\n--- Todas as séries salvas ---");
        all.forEach(series -> System.out.printf(
                "- [id=%d] %s (%s) | nota: %s%n",
                series.getId(), series.getName(), series.getReleaseDate(), series.getVoteAverage()));
        System.out.println();
    }

    private void searchAndCache() {
        String title = askTitle();
        try {
            List<Movie> saved = movieService.searchAndCache(title);

            if (saved.isEmpty()) {
                System.out.println("Nenhum resultado encontrado para salvar.\n");
                return;
            }

            System.out.println("\n--- Filmes salvos/atualizados no banco ---");
            saved.forEach(movie -> System.out.printf(
                    "- [id=%d] %s (%s)%n",
                    movie.getId(), movie.getName(), movie.getReleaseDate()));
            System.out.println();

        } catch (Exception e) {
            System.out.println("Erro ao buscar/salvar: " + e.getMessage() + "\n");
        }
    }

    private void searchLocal() {
        String title = askTitle();
        List<Movie> results = movieService.searchLocal(title);

        if (results.isEmpty()) {
            System.out.println("Nenhum filme encontrado no banco local.\n");
            return;
        }

        System.out.println("\n--- Resultados no banco local ---");
        results.forEach(movie -> System.out.printf(
                "- [id=%d] %s (%s)%n",
                movie.getId(), movie.getName(), movie.getReleaseDate()));
        System.out.println();
    }

    private void listAllLocal() {
        List<Movie> all = movieService.listAllLocal();

        if (all.isEmpty()) {
            System.out.println("Nenhum filme salvo no banco ainda.\n");
            return;
        }

        System.out.println("\n--- Todos os filmes salvos ---");
        all.forEach(movie -> System.out.printf(
                "- [id=%d] %s (%s) | nota: %s%n",
                movie.getId(), movie.getName(), movie.getReleaseDate(), movie.getVoteAverage()));
        System.out.println();
    }

    private void registerWatch() {
        System.out.print("Tipo (1 = Filme, 2 = Série): ");
        String typeChoice = scanner.nextLine().trim();

        TitleType titleType;
        if (typeChoice.equals("1")) {
            titleType = TitleType.MOVIE;
        } else if (typeChoice.equals("2")) {
            titleType = TitleType.SERIES;
        } else {
            System.out.println("Opção inválida.\n");
            return;
        }

        System.out.print("ID do título (use as opções 3/4 para consultar IDs salvos): ");
        long titleId;
        try {
            titleId = Long.parseLong(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.\n");
            return;
        }

        System.out.print("Data assistida (formato AAAA-MM-DD, ou Enter para hoje): ");
        String dateInput = scanner.nextLine().trim();
        LocalDate watchedAt;
        try {
            watchedAt = dateInput.isBlank() ? LocalDate.now() : LocalDate.parse(dateInput);
        } catch (DateTimeParseException e) {
            System.out.println("Data inválida, use o formato AAAA-MM-DD.\n");
            return;
        }

        System.out.print("Comentário (opcional): ");
        String comment = scanner.nextLine().trim();

        WatchEntry entry = watchEntryService.registerWatch(titleType, titleId, watchedAt,
                comment.isBlank() ? null : comment);

        long totalWatches = watchEntryService.countWatches(titleType, titleId);

        System.out.printf("Visualização registrada! [id=%d] Total de vezes assistido: %d%n%n",
                entry.getId(), totalWatches);
    }

    private void listWatchedSeries() {
        List<WatchEntry> entries = watchEntryService.listAllSeriesWatches();

        if (entries.isEmpty()) {
            System.out.println("Nenhuma série assistida registrada ainda.\n");
            return;
        }

        // agrupa as sessões por titleId para mostrar o nome da série + contagem total
        Map<Long, List<WatchEntry>> grouped = entries.stream()
                .collect(Collectors.groupingBy(WatchEntry::getTitleId));

        System.out.println("\n--- Séries assistidas ---");

        for (Map.Entry<Long, List<WatchEntry>> group : grouped.entrySet()) {
            long seriesId = group.getKey();
            List<WatchEntry> sessions = group.getValue();

            String seriesName = seriesRepository.findById(seriesId)
                    .map(Series::getName)
                    .orElse("(série removida, id=" + seriesId + ")");

            System.out.printf("%s — assistida %d vez(es)%n", seriesName, sessions.size());

            for (WatchEntry session : sessions) {
                System.out.printf("    • %s — %s%n",
                        session.getWatchedAt(),
                        session.getComment() != null ? session.getComment() : "(sem comentário)");
            }
        }
        System.out.println();
    }
}