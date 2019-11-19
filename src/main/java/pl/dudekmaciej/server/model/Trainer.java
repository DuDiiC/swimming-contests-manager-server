package pl.dudekmaciej.server.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.dudekmaciej.server.model.serializer.TrainerSerializer;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonSerialize(using = TrainerSerializer.class)
public class Trainer {
    @Id
    @GeneratedValue
    private Long licenceNr;

    @NotNull
    private String name;

    @NotNull
    private String surname;

    // many trainers one club
    @ManyToOne
    private Club club;

    @Override
    public String toString() {
        return name + " " + surname;
    }

}
