package by.rest.messenger.exception;

public class MessageNotFoundException extends GenericException {

    public MessageNotFoundException(Long id) {
        super("Message with ID " + id + " not found!", 404);
    }

}