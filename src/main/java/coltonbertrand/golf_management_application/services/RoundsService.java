package coltonbertrand.golf_management_application.services;

import coltonbertrand.golf_management_application.classes.Courses;
import coltonbertrand.golf_management_application.classes.Round_Holes;
import coltonbertrand.golf_management_application.classes.Rounds;
import coltonbertrand.golf_management_application.classes.Users;
import coltonbertrand.golf_management_application.repositories.CoursesRepository;
import coltonbertrand.golf_management_application.repositories.RoundsRepository;
import coltonbertrand.golf_management_application.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoundsService {

    @Autowired
    RoundsRepository roundsRepository;
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    CoursesRepository coursesRepository;
    public Rounds addRound(Rounds round,String course_name,Integer user_id){
        Users user = usersRepository.findById(user_id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + user_id));
        Courses course = coursesRepository.findByUserIdAndCourseName(user_id,course_name);
        List<Round_Holes> holes = round.getRoundHolesList();
        round.setUser(user);
        round.setCourse(course);
        int round_score = 0;
        for(Round_Holes hole : holes){
            round_score += hole.getHoleScore();
            hole.setRound(round);
        }
        round.setRoundScore(round_score);
        return roundsRepository.save(round);
    }


}
