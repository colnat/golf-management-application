package coltonbertrand.golf_management_application.services;

import coltonbertrand.golf_management_application.classes.Course_Holes;
import coltonbertrand.golf_management_application.classes.Courses;
import coltonbertrand.golf_management_application.classes.Users;
import coltonbertrand.golf_management_application.repositories.CoursesRepository;
import coltonbertrand.golf_management_application.repositories.UsersRepository;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
//Add to addCourse to validate the user has not saved a course with that name before
@Service
public class CoursesService {
    @Autowired
    private CoursesRepository coursesRepository;
    @Autowired
    private UsersRepository usersRepository;
    public Courses addCourse(Courses course, Integer userId){
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
        course.setUser(user);
        List<Course_Holes> holes = course.getCourseHolesList();
       int course_par = 0;

        for(Course_Holes hole : holes ){
            course_par+= hole.getCourseHolePar();
            hole.setCourse(course);
        }
        course.setCoursePar(course_par);
        course.setCourseType(holes.size());
        return coursesRepository.save(course);
    }
    //int course_par = holes.stream().mapToInt(Course_Holes::getCourseHolePar).sum();
    public List<Courses> getCourses(Integer user_id){
        return coursesRepository.findByUserId(user_id);
    }
}
