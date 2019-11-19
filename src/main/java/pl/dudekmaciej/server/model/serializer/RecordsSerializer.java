package pl.dudekmaciej.server.model.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import pl.dudekmaciej.server.model.Record;

import java.io.IOException;

/**
 * Serializer class, extends {@link StdSerializer} - creates JSON object from database {@link Record} object.
 */
public class RecordsSerializer extends StdSerializer<Record> {
    public RecordsSerializer(Class<Record> c) {
        super(c);
    }

    public RecordsSerializer() {
        this(null);
    }

    @Override
    public void serialize(Record record, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();

        jsonGenerator.writeNumberField("id", record.getId());
        jsonGenerator.writeNumberField("minutes", record.getMinutes());
        jsonGenerator.writeNumberField("seconds", record.getSeconds());
        jsonGenerator.writeNumberField("hundredths", record.getHundredths());
        jsonGenerator.writeNumberField("competitorPesel", record.getCompetitor().getPesel());
        jsonGenerator.writeNumberField("competitionId", record.getCompetition().getId());

        jsonGenerator.writeEndObject();
    }
}
