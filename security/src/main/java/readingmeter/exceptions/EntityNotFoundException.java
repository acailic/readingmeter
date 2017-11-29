package readingmeter.exceptions;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String id) {
        super("could not find entity id'" + id + "'.");
    }
}
