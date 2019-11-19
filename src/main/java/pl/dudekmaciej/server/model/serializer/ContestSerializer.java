package pl.dudekmaciej.server.model.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import pl.dudekmaciej.server.model.Competition;
import pl.dudekmaciej.server.model.Competitor;
import pl.dudekmaciej.server.model.Contest;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Serializer class, extends {@link StdSerializer} - creates JSON object from database {@link Contest} object.
 */
public class ContestSerializer extends StdSerializer<Contest> {
    public ContestSerializer(Class<Contest> c) {
        super(c);
    }

    public ContestSerializer() {
        this(null);
    }

    @Override
    public void serialize(Contest contest, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        List<String> competitions = contest.getCompetitions()
                .stream()
                .map(Competition::getId)
                .map(id -> id.toString())
                .collect(Collectors.toList());


        List<String> competitors = contest.getCompetitors()
                .stream()
                .map(Competitor::getPesel)
                .map(pesel -> pesel.toString())
                .collect(Collectors.toList());

        jsonGenerator.writeStartObject();

        jsonGenerator.writeNumberField("id", contest.getId());
        jsonGenerator.writeStringField("name", contest.getName());
        jsonGenerator.writeStringField("date", contest.getDate().format(DateTimeFormatter.ofPattern("YYYY-MM-dd")));
        jsonGenerator.writeStringField("city", contest.getCity());
        jsonGenerator.writeArrayFieldStart("competitionsIds");
        jsonGenerator.writeRaw(String.join(", ", competitions));
        jsonGenerator.writeEndArray();
        jsonGenerator.writeArrayFieldStart("competitorsIds");
        jsonGenerator.writeRaw(String.join(", ", competitors));
        jsonGenerator.writeEndArray();

        jsonGenerator.writeEndObject();
    }
}
