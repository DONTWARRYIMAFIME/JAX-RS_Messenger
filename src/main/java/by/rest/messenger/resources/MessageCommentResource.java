package by.rest.messenger.resources;

import by.rest.messenger.dto.MessageCommentDTO;
import by.rest.messenger.model.entity.MessageComment;
import by.rest.messenger.service.MessageCommentService;
import by.rest.messenger.service.UserService;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MessageCommentResource {

    private final UserService userService = new UserService();
    private final MessageCommentService messageCommentService = new MessageCommentService();

    @GET
    public List<MessageCommentDTO.Response> getComments(@PathParam("messageId") Long messageId,
                                                        @Context UriInfo uriInfo) {

        return messageCommentService
                .getAllComments(messageId)
                .stream()
                .map((c) -> new MessageCommentDTO.Response(uriInfo, c))
                .collect(Collectors.toList());
    }

    @GET
    @Path("/{commentNumber}")
    public MessageCommentDTO.Response getComment(@PathParam("messageId") Long messageId,
                                                 @PathParam("commentNumber") Long commentNumber,
                                                 @Context UriInfo uriInfo) {
        MessageComment messageComment = messageCommentService.getComment(messageId, commentNumber);
        return new MessageCommentDTO.Response(uriInfo, messageComment);
    }

    @POST
    public Response createComment(@PathParam("messageId") Long messageId,
                                  MessageCommentDTO.Request requestComment,
                                  @Context UriInfo uriInfo) {
        MessageComment messageComment = new MessageComment(
                requestComment.getContent(),
                userService.getUserByLogin(requestComment.getAuthor())
        );

        messageComment = messageCommentService.createComment(messageId, messageComment);

        URI uri = uriInfo
                .getAbsolutePathBuilder()
                .path(messageComment.getId().toString())
                .build();

        return Response
                .created(uri)
                .entity(new MessageCommentDTO.Response(uriInfo, messageComment))
                .build();
    }

    @PUT
    @Path("/{commentNumber}")
    public MessageCommentDTO.Response updateComment(@PathParam("messageId") Long messageId,
                                                    @PathParam("commentNumber") Long commentNumber,
                                                    MessageCommentDTO.Request requestComment,
                                                    @Context UriInfo uriInfo) {
        MessageComment messageComment = new MessageComment(
                commentNumber,
                requestComment.getContent()
        );

        messageComment = messageCommentService.updateComment(messageId, messageComment);
        return new MessageCommentDTO.Response(uriInfo, messageComment);
    }

    @DELETE
    @Path("/{commentNumber}")
    public void deleteComment(@PathParam("messageId") Long messageId,
                              @PathParam("commentNumber") Long commentNumber) {
        messageCommentService.deleteComment(messageId, commentNumber);
    }

}
