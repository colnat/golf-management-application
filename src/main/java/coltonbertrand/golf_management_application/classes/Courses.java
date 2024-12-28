package coltonbertrand.golf_management_application.classes;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name="user_courses")
public class Courses implements Serializable {
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
    private Integer coursePar;

    @Column(name = "course_type")
    private Integer courseType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private Users user;


    @OneToMany(mappedBy = "course",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Course_Holes> courseHolesList;

}
