package pl.dudekmaciej.server.model.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import pl.dudekmaciej.server.model.Competitor;

import java.io.IOException;

/**
 * Serializer class, extends {@link StdSerializer} - creates JSON object from database {@link Competitor} object.
 */
public class CompetitorSerializer extends StdSerializer<Competitor> {
    public CompetitorSerializer(Class<Competitor> c) {
        super(c);
    }

    public CompetitorSerializer() {
        this(null);
    }

    @Override
    public void serialize(Competitor competitor, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();

        jsonGenerator.writeNumberField("pesel", competitor.getPesel());
        jsonGenerator.writeStringField("name", competitor.getName());
        jsonGenerator.writeStringField("surname", competitor.getSurname());
        jsonGenerator.writeStringField("gender", competitor.getGender());
        jsonGenerator.writeNumberField("clubId", competitor.getClub().getId());

        jsonGenerator.writeEndObject();
    }
}
