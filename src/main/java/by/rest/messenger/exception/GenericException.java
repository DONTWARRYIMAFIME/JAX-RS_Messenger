package by.rest.messenger.exception;

import by.rest.messenger.model.ErrorMessage;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

@Slf4j
public class GenericException extends WebApplicationException {

    public GenericException(String message, int code) {
        super(generateResponse(generateErrorMessage(message, code), code));
    }

    private static ErrorMessage generateErrorMessage(String message, int code) {
        ErrorMessage errorMessage = new ErrorMessage(message, code);
        log.error(errorMessage.toString());
        return errorMessage;
    }

    private static Response generateResponse(ErrorMessage errorMessage, int code) {
        return Response
                .status(code)
                .entity(errorMessage)
                .build();
    }

}
