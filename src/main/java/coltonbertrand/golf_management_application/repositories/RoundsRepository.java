package coltonbertrand.golf_management_application.repositories;

import coltonbertrand.golf_management_application.classes.Rounds;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoundsRepository extends JpaRepository<Rounds,Integer> {
    List<Rounds> findByUserId(Integer user_id);
    List<Rounds> findByCourseId(Integer course_id);
}
