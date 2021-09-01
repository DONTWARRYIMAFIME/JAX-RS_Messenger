package by.rest.messenger.hateoas;

import by.rest.messenger.model.Link;
import by.rest.messenger.model.entity.Message;
import by.rest.messenger.resources.MessageCommentResource;
import by.rest.messenger.resources.MessageResource;
import by.rest.messenger.resources.UserResource;

import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

public class MessageLinks {

    private MessageLinks() {}

    public static List<Link> getLinks(UriInfo uriInfo, Message message) {
        List<Link> links = new ArrayList<>();

        links.add(new Link(getUriForSelf(uriInfo, message.getId()), "self"));
        links.add(new Link(getUriForUser(uriInfo, message.getUser().getLogin()), "user"));
        links.add(new Link(getUriForComments(uriInfo, message.getId()), "comments"));

        return links;
    }

    private static String getUriForSelf(UriInfo uriInfo, Long id) {
        return uriInfo
                .getBaseUriBuilder()
                .path(MessageResource.class)
                .path(id.toString())
                .build()
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
                .path(MessageResource.class, "getCommentResource")
                .path(MessageCommentResource.class)
                .resolveTemplate("messageId", id)
                .build()
                .toString();
    }


}
