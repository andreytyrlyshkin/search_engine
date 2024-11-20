package searchengine.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import searchengine.dto.statistics.StatisticsResponse;
import searchengine.services.APIService;
import searchengine.services.StatisticsService;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final APIService apiService = new APIService(false);
    private final StatisticsService statisticsService;

    public ApiController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("/statistics")
    public ResponseEntity<StatisticsResponse> statistics() {
        return ResponseEntity.ok(statisticsService.getStatistics());
    }

    @GetMapping("/startIndexing")
    public ResponseEntity<?> startIndexing() {
        if (!apiService.getIsRunning()) {
            apiService.setIsRunning(true);
            return new ResponseEntity<>(apiService.isIndexingAlreadyRunning(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(apiService.isIndexingAlreadyRunning(), HttpStatus.LOCKED);
        }
    }

    @GetMapping("/stopIndexing")
    public ResponseEntity<?> stopIndexing() {
        if (apiService.getIsRunning()) {
            apiService.setIsRunning(false);
            return new ResponseEntity<>(apiService.isIndexingAlreadyStopped(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(apiService.isIndexingAlreadyStopped(), HttpStatus.LOCKED);
        }
    }
}
