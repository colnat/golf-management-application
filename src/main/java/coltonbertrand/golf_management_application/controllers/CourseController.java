package coltonbertrand.golf_management_application.controllers;

import coltonbertrand.golf_management_application.classes.Courses;
import coltonbertrand.golf_management_application.classes.Users;
import coltonbertrand.golf_management_application.services.CoursesService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:5173/", allowCredentials = "true")
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CoursesService coursesService;

    @PostMapping("/saveCourse")
    public ResponseEntity<Courses> addCourse(@Valid @RequestBody Courses course, HttpSession session) {
        Users user = (Users) session.getAttribute("user");
        System.out.println(course);
        Courses savedCourse = coursesService.addCourse(course, user.getId());
        return ResponseEntity.ok().body(savedCourse);
    }

    @GetMapping("/getCourses")
    public ResponseEntity<List<Courses>> getCourses(HttpSession session) {
        Users user = (Users) session.getAttribute("user");
        List<Courses> getUserCourses = coursesService.getCourses(user.getId());
        return ResponseEntity.ok().body(getUserCourses);
    }

    @DeleteMapping("/delete-course/{courseId}")
    public ResponseEntity<?> deleteCourse(@PathVariable Integer courseId, HttpSession session) {
        Users user = (Users) session.getAttribute("user");
        System.out.println(courseId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        coursesService.deleteCourse(courseId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/favourite-course")
    public ResponseEntity<Optional<Courses>> favouriteCourse(HttpSession session) {
        Users user = (Users) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        Optional<Courses> getFavouriteCourse = coursesService.findFavouriteCourse(user.getId());
        return ResponseEntity.ok().body(getFavouriteCourse);
    }

    @GetMapping("/most-played-course")
    public ResponseEntity<Optional<Courses>> mostPlayedCourse(HttpSession session) {
        Users user = (Users) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        Optional<Courses> mostPlayed = coursesService.mostPlayedCourse(user.getId());
        return ResponseEntity.ok().body(mostPlayed);
    }

}
