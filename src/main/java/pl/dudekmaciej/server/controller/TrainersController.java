package pl.dudekmaciej.server.controller;

import elemental.json.Json;
import elemental.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.dudekmaciej.server.model.Club;
import pl.dudekmaciej.server.model.Trainer;
import pl.dudekmaciej.server.model.exception.EntityNotFoundException;
import pl.dudekmaciej.server.repository.ClubsRepository;
import pl.dudekmaciej.server.repository.TrainersRepository;
import pl.dudekmaciej.server.util.ValueConverter;

import java.util.List;

/**
 * REST Controller using {@link TrainersRepository} to receive HTTP requests for {@link Trainer}s.
 */
@RestController
@RequestMapping("/api/trainer")
public class TrainersController extends DataController<Trainer> {

    @Autowired
    private TrainersRepository trainersRepository;

    @Autowired
    private ClubsRepository clubsRepository;

    /**
     * Returns all {@link Trainer}s from the database.
     */
    @GetMapping("")
    public List<Trainer> getAllTrainers() {
        return trainersRepository.findAll();
    }

    /**
     * Returns all {@link Trainer}s from the selected {@link Club} from the database.
     */
    @GetMapping("/club/{clubId}")
    public List<Trainer> getAllTrainersByClub(@PathVariable Long clubId) {
        return trainersRepository.findAllByClub_Id(clubId);
    }

    /**
     * Returns one {@link Trainer} using selected id from the database.
     */
    @GetMapping("/{id}")
    public Trainer getOneTrainer(@PathVariable Long id) throws EntityNotFoundException {
        if (!trainersRepository.existsById(id)) throw new EntityNotFoundException(Trainer.class, id);
        return trainersRepository.getOne(id);
    }

    /**
     * Adds new {@link Trainer} to the database.
     */
    @PostMapping("")
    public Trainer addTrainer(@RequestBody String body) throws EntityNotFoundException {
        return trainersRepository.save(parseObject(body));
    }

    /**
     * Updates the selected {@link Trainer} in the database.
     */
    @PutMapping("")
    public Trainer updateTrainer(@RequestBody String body) throws EntityNotFoundException {
        Trainer updatedTrainer = parseObject(body);
        if (!trainersRepository.existsById(updatedTrainer.getLicenceNr())) throw new EntityNotFoundException(Trainer.class, updatedTrainer.getLicenceNr());

        Trainer existingTrainer = trainersRepository.getOne(updatedTrainer.getLicenceNr());
        existingTrainer.setName(updatedTrainer.getName());
        existingTrainer.setSurname(updatedTrainer.getSurname());

        return trainersRepository.save(existingTrainer);
    }

    /**
     * Removes the selected {@link Trainer} from the database.
     */
    @DeleteMapping("/{id}")
    public Trainer removeTrainer(@PathVariable Long id) throws EntityNotFoundException {
        if (!trainersRepository.existsById(id)) throw new EntityNotFoundException(Trainer.class, id);

        Trainer trainer = trainersRepository.getOne(id);
        trainersRepository.deleteById(id);
        return trainer;
    }

    /**
     * Parses the {@link Trainer} object from the received input in JSON format.
     */
    @Override
    protected Trainer parseObject(String input) throws EntityNotFoundException {
        JsonObject payload = Json.parse(input);

        Long clubId = ValueConverter.toLong(payload.getNumber("clubId"));
        if (!clubsRepository.existsById(clubId)) throw new EntityNotFoundException(Club.class, clubId);
        Club club = clubsRepository.getOne(clubId);

        Long licenceNr;
        if (payload.hasKey("licenceNr")) licenceNr = ValueConverter.toLong(payload.getNumber("licenceNr"));
        else licenceNr = 0L;

        return new Trainer(
                licenceNr,
                payload.getString("name"),
                payload.getString("surname"),
                club
        );
    }
}
