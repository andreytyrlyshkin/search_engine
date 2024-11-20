package searchengine.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import searchengine.dto.statistics.SiteDto;
import searchengine.exceptions.ResourceNotFoundException;
import searchengine.model.Page;
import searchengine.model.Site;
import searchengine.model.Status;
import searchengine.repositories.PageRepository;
import searchengine.repositories.SiteRepository;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SiteCRUDService implements CRUDService<SiteDto> {

    private final PageRepository pageRepository;
    private final SiteRepository siteRepository;

    @Override
    public SiteDto getById(Long id) {
        log.info("Got the site by id: " + id);
        Site site = siteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Site with id " + id + " is not found"));
        site.setStatus(Status.INDEXED);
        return mapToDto(site);
    }

    @Override
    public Collection<SiteDto> getAll()
    {
        return siteRepository
                .findAll()
                .stream()
                .map(SiteCRUDService::mapToDto)
                .toList();
    }

    @Override
    public void create(SiteDto siteDto) {
        log.info("New site created");
        Site site = mapToEntity(siteDto);
        site.setStatus(Status.INDEXING);
        siteRepository.save(site);
    }

    @Override
    public void update(SiteDto siteDto) {
        Long siteId = siteDto.getId();
        siteRepository.findById(siteId)
                .orElseThrow(() -> new ResourceNotFoundException("Site with id " + siteId + " is not found."));
        log.info("Site with id " + siteId + " updated");
        siteRepository.save(mapToEntity(siteDto));
    }

    @Override
    public void delete(Long id) {
        Site site = siteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Site with id " + id + " is not found."));
        log.info("Site with id " + id + " deleted");
        List<Page> pageList = pageRepository.findBySite(site);
        for(Page page : pageList){
            pageRepository.deleteById(page.getId());
        }
        siteRepository.delete(site);
    }

    public static SiteDto mapToDto(Site site)
    {
        SiteDto siteDto = new SiteDto();
        siteDto.setId(site.getId());
        siteDto.setStatus(site.getStatus());
        siteDto.setStatusTime(Instant.now());
        siteDto.setLastError(site.getLastError());
        siteDto.setUrl(site.getUrl());
        siteDto.setName(site.getName());
        siteDto.setPages(site.getPages()
                .stream()
                .map(PageCRUDService::mapToDto)
                .toList());
        return siteDto;
    }

    public static Site mapToEntity(SiteDto siteDto)
    {
        Site site = new Site();
        site.setId(siteDto.getId());
        site.setStatus(siteDto.getStatus());
        site.setStatusTime(Instant.now());
        site.setLastError(siteDto.getLastError());
        site.setUrl(siteDto.getUrl());
        site.setName(siteDto.getName());
        site.setPages(siteDto.getPages()
                .stream()
                .map(PageCRUDService::mapToEntity)
                .toList());
        return site;
    }
}
