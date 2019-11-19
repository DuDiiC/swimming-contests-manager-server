package pl.dudekmaciej.server.controller;

import elemental.json.Json;
import elemental.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.dudekmaciej.server.model.Competition;
import pl.dudekmaciej.server.model.Competitor;
import pl.dudekmaciej.server.model.Record;
import pl.dudekmaciej.server.model.exception.EntityNotFoundException;
import pl.dudekmaciej.server.repository.CompetitionsRepository;
import pl.dudekmaciej.server.repository.CompetitorsRepository;
import pl.dudekmaciej.server.repository.RecordsRepository;
import pl.dudekmaciej.server.util.ValueConverter;

import javax.transaction.Transactional;
import java.util.List;

/**
 * REST Controller using {@link RecordsRepository} to receive HTTP requests for {@link Record}s.
 */
@RestController
@RequestMapping("/api/record")
public class RecordsController extends DataController<Record> {

    @Autowired
    private RecordsRepository recordsRepository;

    @Autowired
    private CompetitorsRepository competitorsRepository;

    @Autowired
    private CompetitionsRepository competitionsRepository;

    /**
     * Returns all {@link Record}s from the database.
     */
    @GetMapping("")
    public List<Record> getAllRecords() {
        return recordsRepository.findAll();
    }

    /**
     * Returns all {@link Record}s by selected {@link Competitor}.
     */
    @GetMapping("/competitor/{pesel}")
    public List<Record> getAllRecordsByCompetitor(@PathVariable Long pesel) {
        return recordsRepository.findAllByCompetitor_Pesel(pesel);
    }

    /**
     * Returns the best {@link Record} by the {@link Competition} from the database.
     */
    @GetMapping("/competition/{competitionId}")
    public Record getBestRecordByCompetition(@PathVariable Long competitionId) {
        return recordsRepository.getTopByCompetition_IdOrderByMinutesAscSecondsAscHundredthsAsc(competitionId);
    }

    /**
     * Returns one {@link Record} using selected id from the database.
     */
    @GetMapping("/{id}")
    public Record getOneRecord(@PathVariable Long id) throws EntityNotFoundException {
        if (!recordsRepository.existsById(id)) throw new EntityNotFoundException(Record.class, id);

        return recordsRepository.getOne(id);
    }

    /**
     * Adds new {@link Record} to the database.
     */
    @Transactional
    @PostMapping("")
    public Record addRecord(@RequestBody String body) throws EntityNotFoundException {
        Record newRecord = parseObject(body);

        if (recordsRepository.existsByCompetition_IdAndCompetitor_Pesel(newRecord.getCompetition().getId(), newRecord.getCompetitor().getPesel())) {
            Record oldRecord = recordsRepository.getByCompetition_IdAndCompetitor_Pesel(newRecord.getCompetition().getId(), newRecord.getCompetitor().getPesel());
            if (newRecord.compareTo(oldRecord) < 0) {
                recordsRepository.delete(oldRecord);
                return recordsRepository.save(newRecord);
            } else return oldRecord;
        } else return recordsRepository.save(newRecord);
    }

    /**
     * Removes the selected {@link Record} from the database.
     */
    @DeleteMapping("/{id}")
    public Record removeRecord(@PathVariable Long id) throws EntityNotFoundException {
        if (!recordsRepository.existsById(id)) throw new EntityNotFoundException(Record.class, id);

        Record record = recordsRepository.getOne(id);
        recordsRepository.deleteById(id);
        return record;
    }

    /**
     * Parses the {@link Record} object from the received input in JSON format.
     */
    @Override
    protected Record parseObject(String input) throws EntityNotFoundException {
        JsonObject payload = Json.parse(input);

        Long id;
        if (payload.hasKey("id")) id = ValueConverter.toLong(payload.getNumber("id"));
        else id = 0L;

        Long competitorId = ValueConverter.toLong(payload.getNumber("competitorPesel"));
        Long competitionId = ValueConverter.toLong(payload.getNumber("competitionId"));

        if (!competitorsRepository.existsById(competitorId)) throw new EntityNotFoundException(Competitor.class, competitorId);
        if (!competitionsRepository.existsById(competitionId)) throw new EntityNotFoundException(Competition.class, competitionId);

        Competitor competitor = competitorsRepository.getOne(competitorId);
        Competition competition = competitionsRepository.getOne(competitionId);

        return new Record(
                id,
                ValueConverter.toInteger(payload.getNumber("minutes")),
                ValueConverter.toInteger(payload.getNumber("seconds")),
                ValueConverter.toInteger(payload.getNumber("hundredths")),
                competitor,
                competition
        );
    }
}
