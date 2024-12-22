package searchengine.services;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import searchengine.repositories.UrlRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@Slf4j
public class IndexingService extends RecursiveAction {

    /*@Getter
    @Setter
    private static AtomicBoolean isRunning = new AtomicBoolean(false);

    private final String url;

    public IndexingService(String url) {
        this.url = url;
    }

    @Override
    protected void compute() {
        isRunning.set(true);
        Set<String> siteUrls = extractUrls(url);
        if (siteUrls.isEmpty()) {
            return;
        }
        List<IndexingService> taskList = new ArrayList<>();

        for (String siteUrl : siteUrls) {
            IndexingService task = new IndexingService(siteUrl);
            task.fork();
            taskList.add(task);
        }
        taskList.forEach(ForkJoinTask::join);
        isRunning.set(false);
    }

    private Set<String> extractUrls(String url) {
        Set<String> urlSet = new HashSet<>();
        Document doc = getDocumentFromUrl(url);
        if (doc != null) {
            Elements elements = doc.select("a");

            for (Element el : elements) {
                String absUrl = el.attr("abs:href");
                if(absUrl.endsWith("/")){
                    absUrl = absUrl.substring(0, absUrl.length() - 1);
                }
                if (isValidUrl(absUrl)) {
                    urlSet.add(absUrl);
                    UrlRepository.URLS.add(absUrl);
                }
            }
        }
        return urlSet;
    }

    private boolean isValidUrl(String url) {
        return !url.isEmpty()
                && !url.contains("#")
                && !UrlRepository.URLS.contains(url);
                //&& url.startsWith(Main.PARENT_URL); // проверяем, что ссылка соответсовует родительской
    }

    private Document getDocumentFromUrl(String url) {
        try {
            System.out.println("Parsing URL with address: " + url);
            Thread.sleep(150);
            return Jsoup.connect(url).get();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }*/

    public ResponseInterface startIndexing() {
        if (isIndexingAlreadyRunning()) {
            return new Bad(false, "Индексация уже запущена");
        }
        // тут вытащите сайты из конфига,

        // для каждого сайта удалите все данные и измените статус на IDEXING. А потом асинхронно запустите индексацию в другом классе.
        return new Successful(true);

    }
}
