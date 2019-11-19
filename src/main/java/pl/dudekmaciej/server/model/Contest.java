package pl.dudekmaciej.server.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.dudekmaciej.server.model.serializer.ContestSerializer;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonSerialize(using = ContestSerializer.class)
public class Contest {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private LocalDate date;

    @NotNull
    private String city;

    // many contests many competitons
    @ManyToMany
    private Set<Competition> competitions;

    // many contests many competitors
    @ManyToMany
    private Set<Competitor> competitors;

}
