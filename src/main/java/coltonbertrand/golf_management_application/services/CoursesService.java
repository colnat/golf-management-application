package coltonbertrand.golf_management_application.services;

import coltonbertrand.golf_management_application.classes.Course_Holes;
import coltonbertrand.golf_management_application.classes.Courses;
import coltonbertrand.golf_management_application.classes.Rounds;
import coltonbertrand.golf_management_application.classes.Users;
import coltonbertrand.golf_management_application.repositories.CoursesRepository;
import coltonbertrand.golf_management_application.repositories.RoundsRepository;
import coltonbertrand.golf_management_application.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

//Add to addCourse to validate the user has not saved a course with that name before
@Service
public class CoursesService {
    @Autowired
    private CoursesRepository coursesRepository;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private RoundsRepository roundsRepository;

    public Courses addCourse(Courses course, Integer userId){
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
        course.setUser(user);
        List<Course_Holes> holes = course.getCourseHolesList();
        course.setCourseType(holes.size());

    //if it is a 9 hole course find the course par then double it for 18 hole par
        if(holes.size() == 9){
            int nineHolePar = 0;
            for(Course_Holes hole: holes){
                nineHolePar+=hole.getCourseHolePar();
                hole.setCourse(course);
            }
            course.setNineHolePar(nineHolePar);
            course.setEighteenHolePar(nineHolePar*2);
        }
        //if 18 hole course find course par and nine hole par using the first nine holes
        if(holes.size() == 18){
            int eighteenHolePar = 0;
            int nineHolePar = 0;
            for(int i=0;i<holes.size();i++){
              Course_Holes hole = holes.get(i);
              eighteenHolePar+=hole.getCourseHolePar();
              hole.setCourse(course);
              if(i < 9){
                  nineHolePar+=hole.getCourseHolePar();
              }
            }
            course.setNineHolePar(nineHolePar);
            course.setEighteenHolePar(eighteenHolePar);
        }
        return coursesRepository.save(course);
    }

    //int course_par = holes.stream().mapToInt(Course_Holes::getCourseHolePar).sum();
    public List<Courses> getCourses(Integer user_id){
        return coursesRepository.findByUserId(user_id);
    }

    public void deleteCourse(Integer courseId){
        coursesRepository.deleteById(courseId);
    }

    public Optional<Courses> findFavouriteCourse(Integer userId){
        List<Courses> userCourses = coursesRepository.findByUserId(userId);
        if(userCourses.isEmpty()){
            throw new RuntimeException("User needs at least one course");
        }
        return userCourses.stream().max(Comparator.comparing(Courses::getCourseRating));
    }

   //Finds the course that appears most often in the users rounds and returns that course, along with how many times
    public Optional<Map.Entry<Courses,Long>> mostPlayedCourse(Integer userId){
        List<Rounds> userRounds = roundsRepository.findByUserId(userId);
        if(userRounds.isEmpty()){
            throw new RuntimeException("Must have at least one round");
        }
        return userRounds.stream().collect(Collectors.groupingBy(Rounds::getCourse, Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue());
    }
}
