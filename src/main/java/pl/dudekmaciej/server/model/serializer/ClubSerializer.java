package pl.dudekmaciej.server.model.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import pl.dudekmaciej.server.model.Club;

import java.io.IOException;

/**
 * Serializer class, extends {@link StdSerializer} - creates JSON object from database {@link Club} object.
 */
public class ClubSerializer extends StdSerializer<Club> {
    public ClubSerializer(Class<Club> c) {
        super(c);
    }

    public ClubSerializer() {
        this(null);
    }

    @Override
    public void serialize(Club club, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();

        jsonGenerator.writeNumberField("id", club.getId());
        jsonGenerator.writeStringField("name", club.getName());
        jsonGenerator.writeStringField("city", club.getCity());

        jsonGenerator.writeEndObject();
    }
}
