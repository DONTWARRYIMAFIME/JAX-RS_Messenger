package by.rest.messenger.exception;

public class UserDuplicationException extends GenericException {

    public UserDuplicationException(String login) {
        super("User with LOGIN " + login + " already exists", 409);
    }

}