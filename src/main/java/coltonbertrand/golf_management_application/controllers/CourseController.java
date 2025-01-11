package coltonbertrand.golf_management_application.controllers;

import coltonbertrand.golf_management_application.classes.Courses;
import coltonbertrand.golf_management_application.classes.Users;
import coltonbertrand.golf_management_application.services.CoursesService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173/", allowCredentials = "true")
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CoursesService coursesService;

    @PostMapping("/saveCourse")
    public ResponseEntity<Courses> addCourse(@RequestBody Courses course, HttpSession session){
        Users user = (Users) session.getAttribute("user");
        System.out.println(course);
        Courses savedCourse = coursesService.addCourse(course, user.getId());
        return ResponseEntity.ok().body(savedCourse);
    }

    @GetMapping("/getCourses")
    public  ResponseEntity <List<Courses>> getCourses (HttpSession session){
        Users user = (Users) session.getAttribute("user");
        List<Courses> getUserCourses = coursesService.getCourses(user.getId());
        return ResponseEntity.ok().body(getUserCourses);
    }

    @DeleteMapping("/delete-course/{courseId}")
    public ResponseEntity<?> deleteCourse(@PathVariable Integer courseId, HttpSession session){
        Users user = (Users) session.getAttribute("user");
        System.out.println(courseId);
        if(user == null){
            return ResponseEntity.notFound().build();
        }
        coursesService.deleteCourse(courseId);
        return ResponseEntity.ok().build();
    }
}
