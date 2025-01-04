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


    @PostMapping("/saveRound/{courseId}")
    public ResponseEntity<Rounds> addRound(@RequestBody Rounds round, @PathVariable Integer courseId, HttpSession session){
        Users user = (Users) session.getAttribute("user");
        System.out.println(user);
        System.out.println(round);
        Rounds savedRound = roundsService.addRound(round,courseId,user.getId());
        return ResponseEntity.ok().body(savedRound);
}

}
