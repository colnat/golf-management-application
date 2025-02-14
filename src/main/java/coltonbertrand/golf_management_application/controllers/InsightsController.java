package coltonbertrand.golf_management_application.controllers;

import coltonbertrand.golf_management_application.classes.Rounds;
import coltonbertrand.golf_management_application.classes.Users;
import coltonbertrand.golf_management_application.repositories.RoundsRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173/", allowCredentials = "true")
@RequestMapping("/get-insights")
public class InsightsController {

    @Autowired
    private RoundsRepository roundsRepository;

    private final ChatClient chatClient;

    public InsightsController(ChatClient.Builder builder){

        this.chatClient = builder
                .defaultSystem("Analyze the following stats from a users most recent golf rounds. Pay close attention to the stats" +
                        " If you notice a user is consistently is getting a high amount of three putts. Then suggest a drill to" +
                        " fix that. For a 9 hole round this would be 3 and for a 18 hole round this would be 6" +
                        " If a user is consistently getting a high amount of slices. Give advice on how to fix a slice when driving " +
                        " For a 9 hole round this would be 3 and for a 18 hole round this would be 6"+
                        " When a user is doing a good job on hitting fairways say congrats, also if you notice improvement. Offer other golf tips and general advice too" +
                        " Also tell the user how much they can lower their score if they improve in these areas " +
                        " Recommend courses near the user if they have a location, if not don't mention it" +
                        " Each round is delimited by ```"+
                        " please keep the response below 75 words.")
                .build();
    }

    @GetMapping("/insights")
    public ResponseEntity<String> insights(HttpSession session, @RequestParam(required = false) String userLocation){
            Users user = (Users) session.getAttribute("user");
            List<Rounds> topFiveRounds = roundsRepository.findTop5ByUserIdOrderByDatePlayedDesc(user.getId());
            if (topFiveRounds.isEmpty()) {
                return ResponseEntity.ok().body("Try adding a round to see how you can improve your game");
            }
            StringBuilder prompt = new StringBuilder();
            for(Rounds round : topFiveRounds) {
                prompt.append("```Users Name: %s, User Location: %s, Date Played: %s, Round Length: %d, Three Putts: %d, Slices or Draws: %d, Fairways Hit: %d```"
                        .formatted(round.getUser().getFirstName(), userLocation, round.getDatePlayed(),round.getRoundLength(), round.getThreePutts(), round.getSlicesOrDraws(), round.getFairwaysHit()));
            }
            String response = chatClient.prompt()
                    .user(prompt.toString())
                    .call()
                    .content();
            System.out.println(response);
            return ResponseEntity.ok().body(response);
    }
}
