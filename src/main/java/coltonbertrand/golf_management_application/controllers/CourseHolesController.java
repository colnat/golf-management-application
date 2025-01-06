package coltonbertrand.golf_management_application.controllers;


import coltonbertrand.golf_management_application.classes.Course_Holes;
import coltonbertrand.golf_management_application.services.CourseHolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173/", allowCredentials = "true")
@RequestMapping("/course-holes")
public class CourseHolesController {

   @Autowired
   private CourseHolesService courseHolesService;

    @GetMapping("/get/{courseId}")
    public ResponseEntity<List<Course_Holes>> getCourseHoles(@PathVariable Integer courseId){
        List<Course_Holes> getHoles = courseHolesService.getHoles(courseId);
        return ResponseEntity.ok().body(getHoles);
    }
}
