package coltonbertrand.golf_management_application.controllers;


import coltonbertrand.golf_management_application.classes.Rounds;
import coltonbertrand.golf_management_application.classes.Users;
import coltonbertrand.golf_management_application.repositories.RoundsRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173/", allowCredentials = "true")
@RequestMapping("/get-insights")
public class Insights {

    @Autowired
    private RoundsRepository roundsRepository;

    private final ChatClient chatClient;

    public Insights(ChatClient.Builder builder){

        this.chatClient = builder
                .defaultSystem("Analyze the following stats from a users most recent golf rounds. Pay close attention to the stats" +
                        " If you notice a user is consistently is getting a high amount of three putts. Then suggest a drill to" +
                        " fix that. For a 9 hole round this would be 3 and for a 18 hole round this would be 6" +
                        " If a user is consistently getting a high amount of slices. Give advice on how to fix a slice when driving " +
                        " For a 9 hole round this would be 3 and for a 18 hole round this would be 6"+
                        " When a user is doing a good job on hitting fairways say congrats, also if you notice improvement. Offer other golf tips and general advice too"+
                        " Each round is delimited by ```"+
                        " please keep the response below 75 words.")
                .build();
    }

    @GetMapping("/insights")
    public String insights(HttpSession session){
        Users user = (Users) session.getAttribute("user");
        List<Rounds> topFiveRounds = roundsRepository.findTop5ByUserIdOrderByDatePlayedDesc(user.getId());
        StringBuilder prompt = new StringBuilder();
        for(Rounds round : topFiveRounds) {
            prompt.append("```Users Name: %s, Date Played: %s, Round Length: %d, Three Putts: %d, Slices or Draws: %d, Fairways Hit: %d```"
                    .formatted(round.getUser().getFirstName(),round.getDatePlayed(),round.getRoundLength(), round.getThreePutts(), round.getSlicesOrDraws(), round.getFairwaysHit()));
        }
        String response = chatClient.prompt()
                .user(prompt.toString())
                .call()
                .content();
        System.out.println(response);
        return response;

    }
}
