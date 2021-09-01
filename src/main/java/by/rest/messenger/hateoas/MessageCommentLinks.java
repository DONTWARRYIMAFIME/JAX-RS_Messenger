package by.rest.messenger.hateoas;

import by.rest.messenger.model.Link;
import by.rest.messenger.model.entity.MessageComment;
import by.rest.messenger.resources.MessageCommentResource;
import by.rest.messenger.resources.MessageResource;
import by.rest.messenger.resources.UserResource;

import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

public class MessageCommentLinks {

    private MessageCommentLinks() {}

    public static List<Link> getLinks(UriInfo uriInfo, MessageComment comment) {
        List<Link> links = new ArrayList<>();

        links.add(new Link(getUriForSelf(uriInfo, comment.getMessage().getId(), comment.getId()), "self"));
        links.add(new Link(getUriForUser(uriInfo, comment.getUser().getLogin()), "user"));
        links.add(new Link(getUriForComments(uriInfo, comment.getMessage().getId()), "message"));

        return links;
    }

    private static String getUriForSelf(UriInfo uriInfo, Long messageId, Long id) {
        return uriInfo
                .getBaseUriBuilder()
                .path(MessageResource.class)
                .path(MessageResource.class, "getCommentResource")
                .path(MessageCommentResource.class)
                .resolveTemplate("messageId", messageId)
                .path(id.toString())
                .toString();
    }

    private static String getUriForUser(UriInfo uriInfo, String userLogin) {
        return uriInfo
                .getBaseUriBuilder()
                .path(UserResource.class)
                .path(userLogin)
                .build()
                .toString();
    }

    private static String getUriForComments(UriInfo uriInfo, Long id) {
        return uriInfo
                .getBaseUriBuilder()
                .path(MessageResource.class)
                .path(id.toString())
                .build()
                .toString();
    }

}
