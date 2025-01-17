package coltonbertrand.golf_management_application.controllers;

import coltonbertrand.golf_management_application.classes.Rounds;
import coltonbertrand.golf_management_application.classes.Users;
import coltonbertrand.golf_management_application.services.RoundsService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@CrossOrigin(origins = "http://localhost:5173/", allowCredentials = "true")
@RequestMapping("/rounds")
public class RoundController {

    @Autowired
    private RoundsService roundsService;


    @PostMapping("/saveRound/{courseId}")
    public ResponseEntity<Rounds> addRound(@Valid @RequestBody Rounds round, @PathVariable Integer courseId, HttpSession session){
        Users user = (Users) session.getAttribute("user");
        System.out.println(user);
        System.out.println(round);
        Rounds savedRound = roundsService.addRound(round,courseId,user.getId());
        return ResponseEntity.ok().body(savedRound);
}

    @GetMapping("/getRounds")
    public ResponseEntity<List<Rounds>> getRounds(HttpSession session){
        Users user = (Users) session.getAttribute("user");
        List<Rounds> userRounds = roundsService.getRoundsByUser(user.getId());
        return ResponseEntity.ok().body(userRounds);
    }

    @DeleteMapping("/deleteRound/{roundId}")
    public ResponseEntity<?> deleteRound(@PathVariable Integer roundId, HttpSession session){
        Users user = (Users) session.getAttribute("user");
        if(user == null){
            return ResponseEntity.notFound().build();
        }
        roundsService.deleteRound(roundId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/best-18-hole")
    public ResponseEntity<Optional<Rounds>> findBest18HoleRound(HttpSession session){
        Users user = (Users) session.getAttribute("user");
        if(user == null){
            return ResponseEntity.notFound().build();
        }
        Optional<Rounds> best18HoleRound = roundsService.findBest18HoleRound(user.getId());
        return ResponseEntity.ok().body(best18HoleRound);
    }

    @GetMapping("/best-9-hole")
    public ResponseEntity<Optional<Rounds>> findBest9HoleRound(HttpSession session){
        Users user = (Users) session.getAttribute("user");
        if(user == null){
            return ResponseEntity.notFound().build();
        }
        Optional<Rounds> best9HoleRound = roundsService.findBest9HoleRound(user.getId());
        return ResponseEntity.ok().body(best9HoleRound);
    }

    @GetMapping("/handicap")
    public ResponseEntity<Integer> getHandicap(HttpSession session){
        Users user = (Users) session.getAttribute("user");
        if(user == null){
            return ResponseEntity.notFound().build();
        }
        Integer userHandicap = roundsService.handicap(user.getId());
        return ResponseEntity.ok().body(userHandicap);
    }







}
