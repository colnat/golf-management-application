package coltonbertrand.golf_management_application.classes;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "course_holes")
public class Course_Holes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="course_hole_id")
    private Integer id;

    @Column(name="course_hole_par")
    @NotNull
    @Min(3)
    @Max(5)
    private Integer course_hole_par;

    @Column(name="course_hole_length")
    private Integer course_hole_length;

    @Column(name="course_hole_number")
    @Min(1)
    @Max(18)
    private Integer course_hole_number;

    @JoinColumn(name="course_id")
    @ManyToOne
    private Courses course;



}
