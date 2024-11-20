package searchengine.dto.statistics;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import searchengine.model.Status;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SiteDto {
    private Long id;
    private Status status;
    private Instant statusTime;
    private String lastError;
    private String url;
    private String name;
    private List<PageDto> pages = new ArrayList<>();

    @Override
    public String toString()
    {
        return "{" +
                "\n\"id\": " + id +
                "\n\"status\": " + status +
                "\n\"status_time\": " + statusTime +
                "\n\"last_error\": " + lastError +
                "\n\"url\": " + url +
                "\n\"name\": " + name +
                "\n}\n";
    }
}
