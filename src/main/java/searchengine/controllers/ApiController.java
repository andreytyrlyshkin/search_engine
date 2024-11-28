package searchengine.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import searchengine.dto.statistics.StatisticsResponse;
import searchengine.exceptions.IndexingIsAlreadyRunningException;
import searchengine.services.IndexingService;
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
    public ResponseEntity<StatisticsResponse> statistics() {
        return ResponseEntity.ok(statisticsService.getStatistics());
    }


    @GetMapping("/startIndexing")
    public ResponseEntity<?> startIndexing(){
        try (ForkJoinPool forkJoinPool = new ForkJoinPool()) {
            if(IndexingService.getIsRunning().get()){
                return new ResponseEntity<>(new IndexingIsAlreadyRunningException("Индексация уже запущена"), HttpStatus.LOCKED);
            }
            forkJoinPool.invoke(new IndexingService(PARENT_URL));
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/stopIndexing")
    public ResponseEntity<?> stopIndexing(){
        //???
        if(IndexingService.getIsRunning().get()){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(new IndexingIsAlreadyRunningException("Индексация не запущена"), HttpStatus.LOCKED);
    }
}
