package coltonbertrand.golf_management_application.controllers;

import coltonbertrand.golf_management_application.classes.Rounds;
import coltonbertrand.golf_management_application.classes.Users;
import coltonbertrand.golf_management_application.services.RoundsService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:5173/", allowCredentials = "true")
@RequestMapping("/rounds")
public class RoundController {

    @Autowired
    private RoundsService roundsService;

    //To add a round take the round body then the course name then the http session
    @PostMapping("/saveRound")
    public ResponseEntity<Rounds> addRound(@RequestBody Rounds round, String courseName, HttpSession session){
        Users user = (Users) session.getAttribute("user");
        System.out.println(round);
        Rounds savedRound = roundsService.addRound(round,courseName,user.getId());
        return ResponseEntity.ok().body(savedRound);
    }
}
