package coltonbertrand.golf_management_application.classes;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "round_holes")
public class Round_Holes {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="round_hole_id")
    private Integer round_hole_id;

    @Column(name="hole_score")
    @NotNull
    @Min(1)
    private Integer hole_score;

    @Column(name="round_hole_number")
    @NotNull
    @Min(1)
    @Max(18)
    private Integer round_hole_number;

    @ManyToOne
    @JoinColumn(name="round_id")
    private Rounds round;

}
