package coltonbertrand.golf_management_application.controllers;

import coltonbertrand.golf_management_application.classes.Courses;
import coltonbertrand.golf_management_application.classes.Users;
import coltonbertrand.golf_management_application.services.CoursesService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    CoursesService coursesService;
    @PostMapping("/saveCourse")
    public Courses addCourse(@RequestBody Courses course, HttpSession session){
        Users user = (Users) session.getAttribute("user");
        return coursesService.addCourse(course,user.getId());
    }
}
