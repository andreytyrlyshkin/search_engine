package searchengine.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import searchengine.exceptions.IndexingIsAlreadyRunningException;
import searchengine.exceptions.IndexingIsNotRunningException;

@Slf4j
@Service
public class APIService {

    private final boolean isRunning;

    public APIService (boolean isRunning){
        this.isRunning = isRunning;
    }

    public boolean getIsRunning(){
        return isRunning;
    }

    public boolean setIsRunning(boolean isRunning){
        boolean tmp = !isRunning;
        isRunning = tmp;
        return isRunning;
    }

    public String isIndexingAlreadyRunning() {
        log.info("result: " + isRunning);
        if(isRunning){
            throw(new IndexingIsAlreadyRunningException("'error': \"Индексация уже запущена\""));
        }
        return "result: " + !isRunning;
    }

    public String isIndexingAlreadyStopped() {
        log.info("result: " + isRunning);
        if(!isRunning){
            throw(new IndexingIsNotRunningException("'error': \"Индексация не запущена\""));
        }
        return "result: " + isRunning;
    }
}