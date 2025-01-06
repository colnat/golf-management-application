package coltonbertrand.golf_management_application.services;

import coltonbertrand.golf_management_application.classes.Course_Holes;
import coltonbertrand.golf_management_application.repositories.CourseHolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//This class is used to display the holes
@Service
public class CourseHolesService {
    @Autowired
    private CourseHolesRepository courseHolesRepository;

    public List<Course_Holes> getHoles(Integer courseId){
        return courseHolesRepository.findByCourseId(courseId);
    }
}
