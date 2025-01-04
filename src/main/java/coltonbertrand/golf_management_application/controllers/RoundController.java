package coltonbertrand.golf_management_application.controllers;

import coltonbertrand.golf_management_application.classes.Rounds;
import coltonbertrand.golf_management_application.classes.Users;
import coltonbertrand.golf_management_application.services.RoundsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

//@PostMapping("/saveRound")
//public ResponseEntity<Rounds> addRound(@RequestBody Map<String, Object> requestData, HttpSession session) {
//    Users user = (Users) session.getAttribute("user");
//    System.out.println(requestData);
//    Rounds round = new ObjectMapper().convertValue(requestData.get("round"), Rounds.class);
//    Integer courseId = (Integer) requestData.get("courseId");
//    Rounds savedRound = roundsService.addRound(round, courseId, user.getId());
//    return ResponseEntity.ok().body(savedRound);
//}