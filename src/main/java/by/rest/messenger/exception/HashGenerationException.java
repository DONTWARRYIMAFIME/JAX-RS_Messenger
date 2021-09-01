package by.rest.messenger.exception;

public class HashGenerationException extends GenericException {

    public HashGenerationException(String message, Exception exception) {
        super(message, 500);
        exception.printStackTrace();
    }

}
