package pl.dudekmaciej.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.dudekmaciej.server.model.Record;

import java.util.List;

/**
 * {@link JpaRepository} for {@link Record} objects in database - that interface after adding to
 * {@link pl.dudekmaciej.server.controller.RecordsController} with adnotation {@link org.springframework.beans.factory.annotation.Autowired}
 * add implementation methods automatically.
 */
public interface RecordsRepository extends JpaRepository<Record, Long> {

    Record getTopByCompetition_IdOrderByMinutesAscSecondsAscHundredthsAsc(Long competitionId);

    @Query("select r from Record r join Competitor c on r.competitor = c where c.pesel = :pesel")
    List<Record> findAllByCompetitor_Pesel(@Param("pesel") Long pesel);

    boolean existsByCompetition_IdAndCompetitor_Pesel(Long competitionId, Long competitorId);

    Record getByCompetition_IdAndCompetitor_Pesel(Long competitionId, Long competitorId);
}
