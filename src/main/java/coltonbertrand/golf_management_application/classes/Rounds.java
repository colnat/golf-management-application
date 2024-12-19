package coltonbertrand.golf_management_application.classes;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name="user_rounds")
public class Rounds {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="round_id")
    private Integer id;

    @Column(name = "fairways_hit")
    private Integer fairwaysHit;

    @Column(name = "three_putts")
    private Integer threePutts;

    @Column(name="slices_or_draws")
    private Integer slicesOrDraws;

    @Column(name="round_score")
    @NotNull
    @Min(18)
    private Integer roundScore;

    @Column(name="date_played")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date datePlayed;

    @Column(name = "round_length")
    @NotNull
    private Integer roundLength;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private Users user;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id")
    private Courses course;

    @OneToMany(mappedBy = "round",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Round_Holes> roundHolesList;

}
