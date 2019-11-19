package pl.dudekmaciej.server.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.dudekmaciej.server.model.serializer.CompetitionSerializer;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonSerialize(using = CompetitionSerializer.class)
public class Competition {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String style;

    @NotNull
    private Integer distance;

    @NotNull
    private String gender;

}

