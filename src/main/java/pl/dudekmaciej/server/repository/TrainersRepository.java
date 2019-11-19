package pl.dudekmaciej.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.dudekmaciej.server.model.Trainer;

import java.util.List;

/**
 * {@link JpaRepository} for {@link Trainer} objects in database - that interface after adding to
 * {@link pl.dudekmaciej.server.controller.TrainersController} with adnotation {@link org.springframework.beans.factory.annotation.Autowired}
 * add implementation methods automatically.
 */
public interface TrainersRepository extends JpaRepository<Trainer, Long> {

    @Query("select t from Trainer t join Club c on t.club = c where c.id = :clubId")
    List<Trainer> findAllByClub_Id(@Param("clubId") Long clubId);

//    List<Trainer> findAllByClub_Id(Long clubId);
}
