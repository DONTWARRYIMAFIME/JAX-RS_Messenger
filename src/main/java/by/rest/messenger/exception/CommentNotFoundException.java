package by.rest.messenger.exception;

public class CommentNotFoundException extends GenericException {

    public CommentNotFoundException(Long id) {
        super("Comment with ID " + id + " not found!", 404);
    }

}