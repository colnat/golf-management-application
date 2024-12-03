package coltonbertrand.golf_management_application.services;

import coltonbertrand.golf_management_application.classes.Course_Holes;
import coltonbertrand.golf_management_application.classes.Courses;
import coltonbertrand.golf_management_application.repositories.CoursesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoursesService {
    @Autowired
    CoursesRepository coursesRepository;

    public Courses addCourse(Courses course){
        List<Course_Holes> holes = course.getCourseHolesList();
        //course.setCourseHolesList(holes);
        int course_par = 0;
        for(Course_Holes hole : holes ){
            course_par+= hole.getCourseHolePar();
            hole.setCourse(course);
        }
        course.setCoursePar(course_par);
        return coursesRepository.save(course);
        // int course_par = holes.stream().mapToInt(Course_Holes::getCourse_hole_par).sum();
    }

    public List<Courses> getCourses(Integer user_id){
        return coursesRepository.findByUserId(user_id);
    }
}
