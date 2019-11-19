package pl.dudekmaciej.server.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.dudekmaciej.server.model.exception.EntityNotFoundException;

/**
 * Abstract class for REST controllers for parsing object from the database.
 */
public abstract class DataController<T> {

    /**
     * Parses the database object from the receives input in JSON format.
     */
    protected abstract T parseObject(String input) throws EntityNotFoundException;

    /**
     * Method for handling exception - if any method throws {@link EntityNotFoundException} then
     * will be returned {@link HttpStatus#NOT_FOUND} status.
     */
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public EntityNotFoundException handleException(EntityNotFoundException e) {
        return e;
    }
}
