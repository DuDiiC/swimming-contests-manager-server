package pl.dudekmaciej.server.model.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import pl.dudekmaciej.server.model.exception.EntityNotFoundException;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

/**
 * Serializer class, extends {@link StdSerializer} - creates JSON object with information about {@link EntityNotFoundException} object.
 */
public class EntityNotFoundExceptionSerializer extends StdSerializer<EntityNotFoundException> {
    public EntityNotFoundExceptionSerializer(Class<EntityNotFoundException> c) {
        super(c);
    }

    public EntityNotFoundExceptionSerializer() {
        this(null);
    }

    @Override
    public void serialize(EntityNotFoundException e, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();

        jsonGenerator.writeStringField("message", e.getMessage());
        jsonGenerator.writeStringField("timestamp", e.getDateTime().format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss")));

        jsonGenerator.writeEndObject();
    }
}
