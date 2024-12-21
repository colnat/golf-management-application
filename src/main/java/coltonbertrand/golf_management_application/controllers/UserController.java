package coltonbertrand.golf_management_application.controllers;

import coltonbertrand.golf_management_application.classes.Users;
import coltonbertrand.golf_management_application.services.UsersService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UsersService usersService;

    @PostMapping("/register")
    public Users registerUser(@Valid @RequestBody Users user) {
        return usersService.addUser(user);
    }

    @GetMapping("/login")
    public Users login(@RequestBody Users user, HttpSession session){
           Users authUser = usersService.loginUser(user.getEmail(),user.getPassword());
           session.setAttribute("user",authUser);
           return authUser;
    }
}
