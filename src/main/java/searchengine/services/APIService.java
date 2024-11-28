package searchengine.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import searchengine.dto.statistics.PageDto;
import searchengine.exceptions.ResourceNotFoundException;
import searchengine.model.Page;
import searchengine.model.Site;
import searchengine.model.Status;
import searchengine.repositories.PageRepository;
import searchengine.repositories.SiteRepository;

import java.time.Instant;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class APIService {

    private final SiteRepository siteRepository;
    private final PageRepository pageRepository;

    public void delete(String url) {
        Site site = siteRepository.findByUrl(url)
                .orElseThrow(() -> new ResourceNotFoundException("Site with url " + url + " is not found."));
        log.info("Site with id " + url + " deleted");
        List<Page> pageList = pageRepository.findBySite(site);
        for(Page page : pageList){
            pageRepository.deleteById(page.getId());
        }
        siteRepository.delete(site);
    }

    public void createNewRecordIndexing(){
        Site site = new Site();
        site.setStatus(Status.INDEXING);
        site.setStatusTime(Instant.now());
        site.setUrl("PARENT_URL");
        site.setName("NAME_OF_SITE");
        log.info("Indexing is started.");
        siteRepository.save(site);
    }

    public void createNewRecordIndexed(){
        Site site = new Site();
        site.setStatus(Status.INDEXED);
        site.setStatusTime(Instant.now());
        site.setUrl("PARENT_URL");
        site.setName("NAME_OF_SITE");
        log.info("Indexing is ended.");
        siteRepository.save(site);
    }

    public void createNewRecordFailed(String errorMessage){
        Site site = new Site();
        site.setStatus(Status.FAILED);
        site.setStatusTime(Instant.now());
        site.setUrl("PARENT_URL");
        site.setName("NAME_OF_SITE");
        site.setLastError(errorMessage);
        log.error(errorMessage);
        siteRepository.save(site);
    }

}
