package by.rest.messenger.exception;

public class UserNotFoundException extends GenericException {

    public UserNotFoundException(Long id) {
        super("User with ID " + id + " not found!", 404);
    }

    public UserNotFoundException(String login) {
        super("User with LOGIN " + login + " not found!", 404);
    }

}