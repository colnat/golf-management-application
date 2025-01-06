package coltonbertrand.golf_management_application.services;

import coltonbertrand.golf_management_application.classes.Round_Holes;
import coltonbertrand.golf_management_application.repositories.RoundHolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoundHolesService {
    @Autowired
    private RoundHolesRepository roundHolesRepository;

    public List<Round_Holes> getHoles(Integer roundId){
        return roundHolesRepository.findByRoundId(roundId);
    }
}
