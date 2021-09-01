package by.rest.messenger.exception;

public class MergeException extends GenericException {

    public MergeException(Class<?> type) {
        super("Cannot merge two objects of class " + type, 500);
    }

}
