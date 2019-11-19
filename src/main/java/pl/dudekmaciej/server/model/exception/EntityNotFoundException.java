package pl.dudekmaciej.server.model.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import pl.dudekmaciej.server.model.serializer.EntityNotFoundExceptionSerializer;

import java.time.LocalDateTime;

/**
 * Exception class throwing new exception when will be problem with found data
 * in the database.
 */
@JsonSerialize(using = EntityNotFoundExceptionSerializer.class)
public class EntityNotFoundException extends Exception {

    @Getter private LocalDateTime dateTime;

    /**
     * Constructor with information about not founding object and about dateTime of the exception.
     */
    public EntityNotFoundException(Class<?> invoker, Long id) {
        super(String.format("Nie znaleziono obiektu %s o ID %d", invoker.getSimpleName(), id));
        this.dateTime = LocalDateTime.now();
    }
}
