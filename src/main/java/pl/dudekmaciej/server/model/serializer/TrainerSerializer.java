package pl.dudekmaciej.server.model.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import pl.dudekmaciej.server.model.Trainer;

import java.io.IOException;

/**
 * Serializer class, extends {@link StdSerializer} - creates JSON object from database {@link Trainer} object.
 */
public class TrainerSerializer extends StdSerializer<Trainer> {
    public TrainerSerializer(Class<Trainer> c) {
        super(c);
    }

    public TrainerSerializer() {
        this(null);
    }

    @Override
    public void serialize(Trainer trainer, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();

        jsonGenerator.writeNumberField("licenceNr", trainer.getLicenceNr());
        jsonGenerator.writeStringField("name", trainer.getName());
        jsonGenerator.writeStringField("surname", trainer.getSurname());
        jsonGenerator.writeNumberField("clubId", trainer.getClub().getId());

        jsonGenerator.writeEndObject();
    }
}
