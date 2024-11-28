package searchengine.repositories;
import searchengine.model.Site;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

public class UrlRepository {
    public final static Set<String> URLS = new ConcurrentSkipListSet<>();
}
