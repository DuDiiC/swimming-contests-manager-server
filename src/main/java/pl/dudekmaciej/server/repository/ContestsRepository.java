package pl.dudekmaciej.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dudekmaciej.server.model.Contest;

/**
 * {@link JpaRepository} for {@link Contest} objects in database - that interface after adding to
 * {@link pl.dudekmaciej.server.controller.ContestsController} with adnotation {@link org.springframework.beans.factory.annotation.Autowired}
 * add implementation methods automatically.
 */
public interface ContestsRepository extends JpaRepository<Contest, Long> {

}
