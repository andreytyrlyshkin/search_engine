package searchengine.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import searchengine.model.Site;

import java.util.Optional;

public interface SiteRepository extends JpaRepository <Site, Long>{
    Optional<Site> findById (Long id);
    Optional<Site> findByUrl (String url);
}
