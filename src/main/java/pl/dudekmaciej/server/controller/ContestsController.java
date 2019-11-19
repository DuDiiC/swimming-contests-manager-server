package pl.dudekmaciej.server.controller;

import elemental.json.Json;
import elemental.json.JsonArray;
import elemental.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.dudekmaciej.server.model.Competition;
import pl.dudekmaciej.server.model.Competitor;
import pl.dudekmaciej.server.model.Contest;
import pl.dudekmaciej.server.model.exception.EntityNotFoundException;
import pl.dudekmaciej.server.repository.CompetitionsRepository;
import pl.dudekmaciej.server.repository.CompetitorsRepository;
import pl.dudekmaciej.server.repository.ContestsRepository;
import pl.dudekmaciej.server.util.ValueConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * REST Controller using {@link ContestsRepository} to receive HTTP requests for {@link Contest}s.
 */
@RestController
@RequestMapping("/api/contest")
public class ContestsController extends DataController<Contest> {

    @Autowired
    private ContestsRepository contestsRepository;

    @Autowired
    private CompetitionsRepository competitionsRepository;

    @Autowired
    private CompetitorsRepository competitorsRepository;

    /**
     * Returns all {@link Contest}s from the database.
     */
    @GetMapping("")
    public List<Contest> getAllContests() {
        return contestsRepository.findAll();
    }

    /**
     * Adds new {@link Contest} to the database.
     */
    @PostMapping("")
    public Contest addContest(@RequestBody String body) throws EntityNotFoundException {
        return contestsRepository.save(parseObject(body));
    }

    /**
     * Updates the selected {@link Contest} in the database.
     */
    @PutMapping("")
    public Contest updateContest(@RequestBody String body) throws EntityNotFoundException {
        Contest updatedContest = parseObject(body);

        if (!contestsRepository.existsById(updatedContest.getId())) throw new EntityNotFoundException(Contest.class, updatedContest.getId());

        Contest existingContest = contestsRepository.getOne(updatedContest.getId());
        existingContest.setName(updatedContest.getName());
        existingContest.setCity(updatedContest.getCity());
        existingContest.setCompetitions(updatedContest.getCompetitions());
        existingContest.setCompetitors(updatedContest.getCompetitors());

        return contestsRepository.save(existingContest);
    }

    /**
     * Removes the selected {@link Contest} from the database.
     */
    @DeleteMapping("/{id}")
    public Contest removeContest(@PathVariable Long id) throws EntityNotFoundException {
        if (!contestsRepository.existsById(id)) throw new EntityNotFoundException(Contest.class, id);

        Contest contest = contestsRepository.getOne(id);
        contestsRepository.deleteById(id);
        return contest;
    }

    /**
     * Parses the {@link Contest} object from the received input in JSON format.
     */
    @Override
    protected Contest parseObject(String input) throws EntityNotFoundException {
        JsonObject payload = Json.parse(input);

        Long id;
        if (payload.hasKey("id")) id = ValueConverter.toLong(payload.getNumber("id"));
        else id = 0L;

        JsonArray competitionIds = payload.getArray("competitionsIds");
        Set<Competition> competitions = new HashSet<>();
        for (int element = 0; element < competitionIds.length(); element++) {
            Long competitionId = ValueConverter.toLong(competitionIds.getNumber(element));

            if (!competitionsRepository.existsById(competitionId)) throw new EntityNotFoundException(Competition.class, competitionId);

            Competition competition = competitionsRepository.getOne(competitionId);
            competitions.add(competition);
        }

        JsonArray competitorsIds = payload.getArray("competitorsIds");
        Set<Competitor> competitors = new HashSet<>();
        for (int element = 0; element < competitorsIds.length(); element++) {
            Long competitorId = ValueConverter.toLong(competitorsIds.getNumber(element));

            if (!competitorsRepository.existsById(competitorId)) throw new EntityNotFoundException(Competitor.class, competitorId);

            Competitor competitor = competitorsRepository.getOne(competitorId);
            competitors.add(competitor);
        }

        LocalDate date = LocalDate.parse(payload.getString("date"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        return new Contest(
                id,
                payload.getString("name"),
                date,
                payload.getString("city"),
                competitions,
                competitors
        );
    }
}
