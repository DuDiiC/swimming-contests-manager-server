package pl.dudekmaciej.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dudekmaciej.server.model.Club;

/**
 * {@link JpaRepository} for {@link Club} objects in database - that interface after adding to
 * {@link pl.dudekmaciej.server.controller.ClubsController} with adnotation {@link org.springframework.beans.factory.annotation.Autowired}
 * add implementation methods automatically.
 */
public interface ClubsRepository extends JpaRepository<Club, Long> {
}
