package coltonbertrand.golf_management_application.classes;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name="user_courses")
public class Courses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="course_id")
    private Integer id;

    @Column(name = "course_name")
    @NotBlank
    private String courseName;

    @Column(name="course_rating")
    private Integer courseRating;

    @Column(name="course_location")
    private String courseLocation;

    @Column(name = "course_par")
    @NotNull
    private Integer coursePar;

    @Column(name = "course_type")
    @NotNull
    private Integer courseType;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @OneToMany(mappedBy = "course")
    private List<Course_Holes> courseHolesList;

}
