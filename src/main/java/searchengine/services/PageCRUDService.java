package searchengine.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import searchengine.dto.statistics.PageDto;
import searchengine.dto.statistics.SiteDto;
import searchengine.exceptions.ResourceNotFoundException;
import searchengine.model.Page;
import searchengine.model.Site;
import searchengine.repositories.PageRepository;
import searchengine.repositories.SiteRepository;

import javax.persistence.*;
import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class PageCRUDService implements CRUDService<PageDto> {

    private final PageRepository pageRepository;
    private final SiteRepository siteRepository;

    @Override
    public PageDto getById(Long id)
    {
        Page page = pageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Page with id " + id + " is not found"));
        log.info("Got the page by id: " + id);
        return mapToDto(page);
    }

    @Override
    public Collection<PageDto> getAll()
    {
        log.info("Got all pages");
        return pageRepository
                .findAll()
                .stream()
                .map(PageCRUDService::mapToDto)
                .toList();
    }

    @Override
    public void create(PageDto pageDto)
    {
        Page page = mapToEntity(pageDto);
        Site site = siteRepository.findById(pageDto.getSiteId())
                .orElseThrow(() -> new ResourceNotFoundException("Site with received id is not found."));
        page.setCode(pageDto.getCode());
        page.setPath(pageDto.getPath());
        page.setContent(pageDto.getContent());
        page.setSiteId(site);
        log.info("Page with id " + page.getId() + " updated");
        pageRepository.save(page);
    }

    @Override
    public void update(PageDto pageDto)
    {
        Long id = pageDto.getId();
        Page page = pageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Page with id " + id + " is not found."));
        Site site = siteRepository.findById(pageDto.getSiteId())
                .orElseThrow(() -> new ResourceNotFoundException("Site with received id is not found."));
        page.setCode(pageDto.getCode());
        page.setPath(pageDto.getPath());
        page.setContent(pageDto.getContent());
        page.setSiteId(site);
        log.info("Page with id " + id + " updated");
        pageRepository.save(page);
    }

    @Override
    public void delete(Long id)
    {
        Page page = pageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Page with id " + id + " is not found."));
        log.info("Page with id " + id + " deleted");
        pageRepository.delete(page);
    }

    public static PageDto mapToDto (Page page)
    {
        PageDto pageDto = new PageDto();
        pageDto.setId(page.getId());
        pageDto.setSiteId(page.getSiteId().getId());
        pageDto.setPath(page.getPath());
        pageDto.setCode(page.getCode());
        pageDto.setContent(page.getContent());
        return pageDto;
    }

    public static Page mapToEntity (PageDto pageDto)
    {
        Page page = new Page();
        page.setId(pageDto.getId());
        page.setPath(pageDto.getPath());
        page.setCode(pageDto.getCode());
        page.setContent(pageDto.getContent());
        //without SiteId
        return page;
    }
}