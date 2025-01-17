package coltonbertrand.golf_management_application.repositories;

import coltonbertrand.golf_management_application.classes.Rounds;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Stream;

@Repository
public interface RoundsRepository extends JpaRepository<Rounds,Integer> {
    List<Rounds> findByUserIdOrderByDatePlayedDesc(Integer userId);
    List<Rounds> findByUserId(Integer userId);
    List<Rounds> findByUserIdAndRoundLength(Integer userId, Integer roundLength);
    List<Rounds> findTop20ByUserIdAndRoundLengthOrderByDatePlayedDesc(Integer userId,Integer roundLength);
}
