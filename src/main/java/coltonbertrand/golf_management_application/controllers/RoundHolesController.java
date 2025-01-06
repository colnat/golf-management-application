package coltonbertrand.golf_management_application.controllers;


import coltonbertrand.golf_management_application.classes.Round_Holes;
import coltonbertrand.golf_management_application.classes.Users;
import coltonbertrand.golf_management_application.services.RoundHolesService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173/", allowCredentials = "true")
@RequestMapping("/round-holes")
public class RoundHolesController {

    @Autowired
    private RoundHolesService roundHolesService;
    @GetMapping("/get/{roundId}")
    public ResponseEntity<List<Round_Holes>> getRoundHoles(Integer roundId, HttpSession session){
        Users user = (Users) session.getAttribute("user");
        List<Round_Holes> getHoles = roundHolesService.getHoles(roundId);
        if(user == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(getHoles);
    }
}
