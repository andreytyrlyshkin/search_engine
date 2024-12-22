package searchengine.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import searchengine.responses.ResponseInterface;
import searchengine.services.StatisticsService;

import java.util.concurrent.ForkJoinPool;

@RestController
@RequestMapping("/api")
public class ApiController {
    public static final String PARENT_URL = "https://sendel.ru/"; // Заглушка: сайты для обхода из конфигурации.
    private final StatisticsService statisticsService;

    public ApiController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("/statistics")
    public ResponseEntity<ResponseInterface> statistics() {
        return ResponseEntity.ok(statisticsService.getStatistics());
    }

    @GetMapping("/startIndexing")
    public ResponseEntity<ResponseInterface> startIndexing() {
        return ResponseEntity.ok(indexingService.startIndexing());
    }

    @GetMapping("/stopIndexing")
    public ResponseEntity<ResponseInterface> stopIndexing() {
        return ResponseEntity.ok(indexingService.stopIndexing());
    }

    @PostMapping(value = "/indexPage")
    @ResponseBody
    public ResponseEntity<ResponseInterface> indexPage(PageUrl pageUrl) {
        return ResponseEntity.ok(indexingService.indexPage(pageUrl.getUrl()));
    }

    @GetMapping("/search")
    public ResponseEntity<ResponseInterface> search(
            @RequestParam("query") String query,
            @RequestParam(value = "site", required = false) String site,
            @RequestParam("offset") Integer offset,
            @RequestParam("limit") Integer limit) {
        return ResponseEntity.ok(searchingService.search(query, site, offset, limit));
    }
}
