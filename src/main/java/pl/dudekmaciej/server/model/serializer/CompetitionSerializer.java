package pl.dudekmaciej.server.model.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import pl.dudekmaciej.server.model.Competition;

import java.io.IOException;

/**
 * Serializer class, extends {@link StdSerializer} - creates JSON object from database {@link Competition} object.
 */
public class CompetitionSerializer extends StdSerializer<Competition> {
    public CompetitionSerializer(Class<Competition> c) {
        super(c);
    }

    public CompetitionSerializer() {
        this(null);
    }

    @Override
    public void serialize(Competition competition, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();

        jsonGenerator.writeNumberField("id", competition.getId());
        jsonGenerator.writeStringField("style", competition.getStyle());
        jsonGenerator.writeNumberField("distance", competition.getDistance());
        jsonGenerator.writeStringField("gender", competition.getGender());

        jsonGenerator.writeEndObject();
    }
}
