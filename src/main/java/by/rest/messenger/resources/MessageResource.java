package by.rest.messenger.resources;

import by.rest.messenger.dto.MessageDTO;
import by.rest.messenger.model.entity.Message;
import by.rest.messenger.service.MessageService;
import by.rest.messenger.service.UserService;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Path("/messages")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MessageResource {

    private final MessageService messageService = new MessageService();
    private final UserService userService = new UserService();

    @GET
    public List<MessageDTO.Response> getMessages(@QueryParam("year") Integer year,
                                                        @QueryParam("start") Integer start,
                                                        @QueryParam("size") Integer size,
                                                        @Context UriInfo uriInfo) {
        List<Message> messages = messageService.getAllMessages();

        if (year != null) {
            messages = messageService.findByYear(year, messages);
        }
        if (start != null && size != null) {
            messages = messageService.findPaginated(start, size, messages);
        }

        return messages
                .stream()
                .map((m) -> new MessageDTO.Response(uriInfo, m))
                .collect(Collectors.toList());
    }

    @GET
    @Path("/{messageId}")
    public MessageDTO.Response getMessage(@PathParam("messageId") Long messageId,
                                                 @Context UriInfo uriInfo) {
        Message message = messageService.getMessageById(messageId);
        return new MessageDTO.Response(uriInfo, message);
    }

    @POST
    public Response createMessage(MessageDTO.Request messageRequest,
                                  @Context UriInfo uriInfo) {
        Message message = new Message(
                messageRequest.getContent(),
                userService.getUserByLogin(messageRequest.getUserLogin())
        );

        message = messageService.createMessage(message);

        URI messageUri = uriInfo
                .getAbsolutePathBuilder()
                .path(message.getId().toString())
                .build();

        return Response
                .created(messageUri)
                .entity(new MessageDTO.Response(uriInfo, message))
                .build();
    }

    @PUT
    @Path("/{messageId}")
    public MessageDTO.Response updateMessage(@PathParam("messageId") Long messageId,
                                                    MessageDTO.Request messageRequest,
                                                    @Context UriInfo uriInfo) {
        Message message = new Message(
                messageId,
                messageRequest.getContent()
        );

        message = messageService.updateMessage(message);
        return new MessageDTO.Response(uriInfo, message);
    }

    @DELETE
    @Path("/{messageId}")
    public void deleteMessage(@PathParam("messageId") Long messageId) {
        messageService.deleteMessage(messageId);
    }

    @Path("/{messageId}/comments")
    public MessageCommentResource getCommentResource() {
        return new MessageCommentResource();
    }

}
