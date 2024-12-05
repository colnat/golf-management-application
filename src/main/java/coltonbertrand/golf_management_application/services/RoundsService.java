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


@Service
public class RoundsService {

    @Autowired
    private RoundsRepository roundsRepository;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private CoursesRepository coursesRepository;
    //add round
    public Rounds addRound(Rounds round,String course_name,Integer userId){
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
        Courses course = coursesRepository.findByUserIdAndCourseName(userId,course_name);
        List<Round_Holes> holes = round.getRoundHolesList(); //get holes played in round
        round.setRoundLength(holes.size());
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
    //If a user wants to view rounds they played at a particular course
    public List<Rounds> getRoundsByCourse(Integer courseId) {
        return roundsRepository.findByCourseId(courseId);
    }
    //get the rounds by user
    public List<Rounds> getRoundsByUser(Integer userId) {
        return roundsRepository.findByUserId(userId);
    }
    //find a users handicap
    public Integer handicap(Integer userId) {
        //only calculating 18 hole handicap cause it's the right way. Using the 20 most recent rounds
        //and averaging the best of 8.
        //can adjust the findByUserIdAndRoundLength to select top 20 by date
        //roundsRepository.findTop20ByUserIdAndRoundLengthOrderByDateDesc(userId, 18);
        List<Rounds> getAllRounds = roundsRepository.findByUserIdAndRoundLength(userId, 18);
        if (getAllRounds.size() < 20) {
            throw new IllegalArgumentException("Must have at least 20 rounds to calculate handicap.");
        }
        List<Rounds> mostRecentRounds = getAllRounds.subList(0, 20);
        List<Integer> scores = new ArrayList<>();
        for (Rounds mostRecentRound : mostRecentRounds) {
         int score = mostRecentRound.getRoundScore() - mostRecentRound.getCourse().getCoursePar();
         scores.add(score);
        }
        Collections.sort(scores);
        int sum = 0;
        for(int i=0;i<8;i++){
            sum += scores.get(i);
        }
        return sum / 8;
    }


}



