package searchengine.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "pages")
public class PageModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "site")
    private SiteModel site;

    @EmbeddedId
    @Column(name = "path", columnDefinition = "TEXT")
    private String path;

    @Column(name = "code")
    private Integer code; //code of http-response

    @Column(name = "content", columnDefinition = "MEDIUMTEXT")
    private String content; //html-code of page
}
