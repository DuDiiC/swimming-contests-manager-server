package pl.dudekmaciej.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dudekmaciej.server.model.Competition;

/**
 * {@link JpaRepository} for {@link Competition} objects in database - that interface after adding to
 * {@link pl.dudekmaciej.server.controller.CompetitionsController} with adnotation {@link org.springframework.beans.factory.annotation.Autowired}
 * add implementation methods automatically.
 */
public interface CompetitionsRepository extends JpaRepository<Competition, Long> {
}
