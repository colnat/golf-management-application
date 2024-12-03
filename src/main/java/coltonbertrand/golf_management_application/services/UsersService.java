package coltonbertrand.golf_management_application.services;

import coltonbertrand.golf_management_application.classes.Users;
import coltonbertrand.golf_management_application.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class UsersService {
    @Autowired
    UsersRepository usersRepository;

    public Users addUser(Users user){
        Users emailExists = usersRepository.findByEmail(user.getEmail());

        if(emailExists != null){
            throw new RuntimeException("Email or Username already exists");
        }
        else {
            String pw_hash = BCrypt.hashpw(user.getPassword(),BCrypt.gensalt());
            user.setPassword(pw_hash);
            return usersRepository.save(user );
        }
    }

    public Users loginUser(String email, String password){
        Users user = usersRepository.findByEmail(email);
        if (user != null && BCrypt.checkpw(password,user.getPassword())){
            return user;
        }
        else{
            throw new RuntimeException("Account was not found");
        }
    }
}
