package pl.dudekmaciej.server.controller;

import elemental.json.Json;
import elemental.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.dudekmaciej.server.model.Club;
import pl.dudekmaciej.server.model.exception.EntityNotFoundException;
import pl.dudekmaciej.server.repository.ClubsRepository;
import pl.dudekmaciej.server.util.ValueConverter;

import java.util.List;

/**
 * REST Controller using {@link ClubsRepository} to receive HTTP requests for {@link Club}s.
 */
@RestController
@RequestMapping("/api/club")
public class ClubsController extends DataController<Club> {

    @Autowired
    private ClubsRepository clubsRepository;

    /**
     * Returns all {@link Club}s from the database.
     */
    @GetMapping("")
    public List<Club> getAllClubs() {
        return clubsRepository.findAll();
    }

    /**
     * Returns one {@link Club} using selected id from the database.
     */
    @GetMapping("/{id}")
    public Club getOneClub(@PathVariable Long id) throws EntityNotFoundException {
        if (!clubsRepository.existsById(id)) throw new EntityNotFoundException(Club.class, id);

        return clubsRepository.getOne(id);
    }

    /**
     * Adds new {@link Club} to the database.
     */
    @PostMapping("")
    public Club addClub(@RequestBody String body) throws EntityNotFoundException {
        return clubsRepository.save(parseObject(body));
    }

    /**
     * Updates the selected {@link Club} in the database.
     */
    @PutMapping("")
    public Club updateClub(@RequestBody String body) throws EntityNotFoundException {
        Club updatedClub = parseObject(body);
        if (!clubsRepository.existsById(updatedClub.getId())) throw new EntityNotFoundException(Club.class, updatedClub.getId());

        Club existingClub = clubsRepository.getOne(updatedClub.getId());
        existingClub.setName(updatedClub.getName());
        existingClub.setCity(updatedClub.getCity());

        return clubsRepository.save(existingClub);
    }

    /**
     * Removes the selected {@link Club} from the database.
     */
    @DeleteMapping("/{id}")
    public Club removeClub(@PathVariable Long id) throws EntityNotFoundException {
        if (!clubsRepository.existsById(id)) throw new EntityNotFoundException(Club.class, id);

        Club club = clubsRepository.getOne(id);
        clubsRepository.deleteById(id);
        return club;
    }

    /**
     * Parses the {@link Club} object from the received input in JSON format.
     */
    @Override
    protected Club parseObject(String input) throws EntityNotFoundException {
        JsonObject payload = Json.parse(input);

        Long id;
        if (payload.hasKey("id")) id = ValueConverter.toLong(payload.getNumber("id"));
        else id = 0L;

        return new Club(
                id,
                payload.getString("name"),
                payload.getString("city")
        );
    }
}
