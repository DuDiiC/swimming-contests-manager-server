package pl.dudekmaciej.server.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.dudekmaciej.server.model.serializer.RecordsSerializer;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonSerialize(using = RecordsSerializer.class)
public class Record implements Comparable<Record> {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private Integer minutes;

    @NotNull
    private Integer seconds;

    @NotNull
    private Integer hundredths;

    // many records one competitor
    @ManyToOne
    private Competitor competitor;

    // many records one competition
    @ManyToOne
    private Competition competition;

    @Override
    public int compareTo(Record o) {
        Double left = (this.minutes.doubleValue() * 60.0) + this.seconds.doubleValue() + (this.hundredths.doubleValue() / 100.0);
        Double right = (o.minutes.doubleValue() * 60.0) + o.seconds.doubleValue() + (o.hundredths.doubleValue() / 100.0);
        return Double.compare(left, right);
    }
}