package pl.dudekmaciej.server.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.dudekmaciej.server.model.serializer.CompetitorSerializer;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonSerialize(using = CompetitorSerializer.class)
public class Competitor {
    @Id
    private Long pesel;

    @NotNull
    private String name;

    @NotNull
    private String surname;

    @NotNull
    private String gender;

    // many competitors one club
    @ManyToOne
    private Club club;

}
