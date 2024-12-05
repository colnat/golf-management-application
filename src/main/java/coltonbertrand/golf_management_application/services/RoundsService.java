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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class RoundsService {

    @Autowired
    private RoundsRepository roundsRepository;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private CoursesRepository coursesRepository;
    public Rounds addRound(Rounds round,String course_name,Integer userId){
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
        Courses course = coursesRepository.findByUserIdAndCourseName(userId,course_name);
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

    public List<Rounds> getRoundsByCourse(Integer courseId) {
        return roundsRepository.findByCourseId(courseId);
    }

    public List<Rounds> getRoundsByUser(Integer userId) {
        return roundsRepository.findByUserId(userId);
    }

    public Integer handicap(Integer userId) {
        List<Rounds> getRounds = roundsRepository.findByUserIdAndRoundLength(userId, 18);
        List<Rounds> mostRecentRounds = getRounds.subList(0, 19);
        List<Integer> scores = new ArrayList<>();
        int sum = 0;
        int handicap;
        if (getRounds.size() < 20) {
            return -101;
        }
        for(int i=0; i<=mostRecentRounds.size();i++){
            int score;
            Rounds round = mostRecentRounds.get(i);
            Integer roundScore = round.getRoundScore();
            Courses course = round.getCourse();
            Integer coursePar = course.getCoursePar();
            score = roundScore - coursePar;
            scores.add(score);
        }
        Collections.sort(scores);
        for(int i=0;i<8;i++){
            sum += scores.get(i);
        }
        handicap = sum / 8;
        return handicap;

    }


}



