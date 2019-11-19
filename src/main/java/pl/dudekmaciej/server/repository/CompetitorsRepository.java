package pl.dudekmaciej.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.dudekmaciej.server.model.Competitor;

import java.util.List;

/**
 * {@link JpaRepository} for {@link Competitor} objects in database - that interface after adding to
 * {@link pl.dudekmaciej.server.controller.CompetitorsController} with adnotation {@link org.springframework.beans.factory.annotation.Autowired}
 * add implementation methods automatically.
 */
public interface CompetitorsRepository extends JpaRepository<Competitor, Long> {

    @Query("select c1 from Competitor c1 join Club c2 on c1.club = c2 where c2.id = :clubId")
    List<Competitor> findAllByClub_Id(@Param("clubId") Long clubId);

//    List<Competitor> findAllByClub_Id(Long clubId);

    void deleteAllByClub_Id(Long clubId);
}
