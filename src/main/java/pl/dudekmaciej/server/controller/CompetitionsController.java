package pl.dudekmaciej.server.controller;

import elemental.json.Json;
import elemental.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.dudekmaciej.server.model.Competition;
import pl.dudekmaciej.server.model.exception.EntityNotFoundException;
import pl.dudekmaciej.server.repository.CompetitionsRepository;
import pl.dudekmaciej.server.util.ValueConverter;

import java.util.List;

/**
 * REST Controller using {@link CompetitionsRepository} to receive HTTP requests for {@link Competition}s.
 */
@RestController
@RequestMapping("/api/competition")
public class CompetitionsController extends DataController<Competition> {

    @Autowired
    private CompetitionsRepository competitionsRepository;

    /**
     * Returns all {@link Competition}s from the database.
     */
    @GetMapping("")
    public List<Competition> getAllCompetitions() {
        return competitionsRepository.findAll();
    }

    /**
     * Returns one {@link Competition} using selected id from the database.
     */
    @GetMapping("/{id}")
    public Competition getOneCompetition(@PathVariable Long id) throws EntityNotFoundException {
        if (!competitionsRepository.existsById(id)) throw new EntityNotFoundException(Competition.class, id);

        return competitionsRepository.getOne(id);
    }

    /**
     * Adds new {@link Competition} to the database.
     */
    @PostMapping("")
    public Competition addCompetition(@RequestBody String body) throws EntityNotFoundException {
        return competitionsRepository.save(parseObject(body));
    }

    /**
     * Removes the selected {@link Competition} from the database.
     */
    @DeleteMapping("/{id}")
    public Competition removeCompetition(@PathVariable Long id) throws EntityNotFoundException {
        if (!competitionsRepository.existsById(id)) throw new EntityNotFoundException(Competition.class, id);

        Competition competition = competitionsRepository.getOne(id);
        competitionsRepository.deleteById(id);
        return competition;
    }

    /**
     * Parses the {@link Competition} object from the received input in JSON format.
     */
    @Override
    protected Competition parseObject(String input) throws EntityNotFoundException {
        JsonObject payload = Json.parse(input);

        Long id;
        if (payload.hasKey("id")) id = ValueConverter.toLong(payload.getNumber("id"));
        else id = 0L;

        return new Competition(
                id,
                payload.getString("style"),
                ValueConverter.toInteger(payload.getNumber("distance")),
                payload.getString("gender")
        );
    }
}
