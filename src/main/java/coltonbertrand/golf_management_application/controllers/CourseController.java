package coltonbertrand.golf_management_application.controllers;

import coltonbertrand.golf_management_application.classes.Courses;
import coltonbertrand.golf_management_application.classes.Users;
import coltonbertrand.golf_management_application.services.CoursesService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:5173/", allowCredentials = "true")
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    CoursesService coursesService;
    @PostMapping("/saveCourse")
    public ResponseEntity<Courses> addCourse(@RequestBody Courses course, HttpSession session){
        Users user = (Users) session.getAttribute("user");
        System.out.println(course);
        Courses savedCourse = coursesService.addCourse(course, user.getId());
        return ResponseEntity.ok().body(savedCourse);
    }
}
