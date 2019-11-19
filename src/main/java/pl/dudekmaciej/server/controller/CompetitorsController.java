package pl.dudekmaciej.server.controller;

import elemental.json.Json;
import elemental.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.dudekmaciej.server.model.Club;
import pl.dudekmaciej.server.model.Competitor;
import pl.dudekmaciej.server.model.exception.EntityNotFoundException;
import pl.dudekmaciej.server.repository.ClubsRepository;
import pl.dudekmaciej.server.repository.CompetitorsRepository;
import pl.dudekmaciej.server.util.ValueConverter;

import javax.transaction.Transactional;
import java.util.List;

/**
 * REST Controller using {@link CompetitorsRepository} to receive HTTP requests for {@link Competitor}.
 */
@RestController
@RequestMapping("/api/competitor")
public class CompetitorsController extends DataController<Competitor> {

    @Autowired
    private CompetitorsRepository competitorsRepository;

    @Autowired
    private ClubsRepository clubsRepository;

    /**
     * Returns all {@link Competitor}s from the database.
     */
    @GetMapping("")
    public List<Competitor> getAllCompetitors() {
        return competitorsRepository.findAll();
    }

    /**
     * Returns all {@link Competitor}s by selected {@link Club} from the database.
     */
    @GetMapping("/club/{clubId}")
    public List<Competitor> getAllCompetitorsByClub(@PathVariable Long clubId) {
        return competitorsRepository.findAllByClub_Id(clubId);
    }

    /**
     * Returns one {@link Competitor} using selected id form the database.
     */
    @GetMapping("/{id}")
    public Competitor getOneCompetitor(@PathVariable Long id) throws EntityNotFoundException {
        if (!competitorsRepository.existsById(id)) throw new EntityNotFoundException(Competitor.class, id);

        return competitorsRepository.getOne(id);
    }

    /**
     * Adds new {@link Competitor} to the database.
     */
    @PostMapping("")
    public Competitor addCompetitor(@RequestBody String body) throws EntityNotFoundException {
        return competitorsRepository.save(parseObject(body));
    }

    /**
     * Updates the selected {@link Competitor} in the database.
     */
    @PutMapping("")
    public Competitor updateCompetitor(@RequestBody String body) throws EntityNotFoundException {
        Competitor updatedCompetitor = parseObject(body);

        if (!competitorsRepository.existsById(updatedCompetitor.getPesel())) throw new EntityNotFoundException(Competitor.class, updatedCompetitor.getPesel());

        Competitor existingCompetitor = competitorsRepository.getOne(updatedCompetitor.getPesel());
        existingCompetitor.setName(updatedCompetitor.getName());
        existingCompetitor.setSurname(updatedCompetitor.getSurname());

        return competitorsRepository.save(existingCompetitor);
    }

    /**
     * Removes the selected {@link Competitor} from the database.
     */
    @DeleteMapping("/{id}")
    public Competitor removeCompetitor(@PathVariable Long id) throws EntityNotFoundException {
        if (!competitorsRepository.existsById(id)) throw new EntityNotFoundException(Competitor.class, id);

        Competitor competitor = competitorsRepository.getOne(id);
        competitorsRepository.deleteById(id);
        return competitor;
    }

    /**
     * Removes all {@link Competitor}s from selected {@link Club} from the database.
     */
    @Transactional
    @DeleteMapping("/club/{clubId}")
    public List<Competitor> removeCompetitorsByClub(@PathVariable Long clubId) {
        List<Competitor> competitors = competitorsRepository.findAllByClub_Id(clubId);
        competitorsRepository.deleteAllByClub_Id(clubId);
        return competitors;
    }

    /**
     * Parses the {@link Competitor} object from the received input in JSON format.
     */
    @Override
    protected Competitor parseObject(String input) throws EntityNotFoundException {
        JsonObject payload = Json.parse(input);

        Long clubId = ValueConverter.toLong(payload.getNumber("clubId"));
        if (!clubsRepository.existsById(clubId)) throw new EntityNotFoundException(Club.class, clubId);
        Club club = clubsRepository.getOne(clubId);

        return new Competitor(
                ValueConverter.toLong(payload.getNumber("pesel")),
                payload.getString("name"),
                payload.getString("surname"),
                payload.getString("gender"),
                club
        );
    }
}