package searchengine.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "sites")
public class SiteModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "status", columnDefinition = "ENUM('INDEXING', 'INDEXED', 'FAILED')")
    @Enumerated(EnumType.STRING)
    private Status status; //status of indexation

    @Column(name = "status_time")
    private Instant statusTime;

    @Column(name = "last_error", columnDefinition = "TEXT")
    private String lastError; // text of error of indexation

    @Column(name = "url", columnDefinition = "VARCHAR(255)")
    private String url; //main page of site

    @Column(name = "name", columnDefinition = "VARCHAR(255)")
    private String name; // name of site

    @OneToMany(mappedBy = "site", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PageModel> pageModels = new ArrayList<>();
}
