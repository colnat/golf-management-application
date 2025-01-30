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
                .defaultSystem("Analyze the following stats from a golf round. Pay close attention to the stats" +
                        "The stats contain how many holes played, number of three putts that round, slices or draws, fairways hit," +
                        "round score, and course par. For an 18 hole round if the number of three putts is above 5 suggest a drill to fix their" +
                        "putting. If the slices or draws are above 5 suggest a drill to stop slicing or drawing the ball" +
                        "when driving. If the fairways hit is above 10 congratulate them. For a 9 hole round 2 for three putts" +
                        "3 for slices or draws and 5 for fairways hit. Add more tips or tricks at your will. Throw in some other" +
                        "golf tips as well. Be creative" +
                        "please keep the response below 75 words.")
                .build();
    }

    @GetMapping("/insights")
    public String insights(HttpSession session){
        Users user = (Users) session.getAttribute("user");
        Rounds round = roundsRepository.findTop1ByUserIdOrderByDatePlayedDesc(user.getId());

        String prompt = "Round Length: %d, Three Putts: %d, Slices or Draws: %d, Fairways Hit: %d"
                .formatted(round.getRoundLength(), round.getThreePutts(), round.getSlicesOrDraws(), round.getFairwaysHit());

        String response = chatClient.prompt()
                .user(prompt)
                .call()
                .content();
        System.out.println(response);
        return response;

    }
}
