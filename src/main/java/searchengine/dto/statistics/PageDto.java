package searchengine.dto.statistics;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import searchengine.model.Site;
import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
public class PageDto {
    private Long id;
    private Long siteId;
    private String path;
    private Integer code;
    private String content;

    @Override
    public String toString()
    {
        return "{" +
                "\n\"id\": " + id +
                "\n\"site_id\": " + siteId +
                "\n\"path\": " + path +
                "\n\"code\": " + code +
                "\n}\n";
    }
}