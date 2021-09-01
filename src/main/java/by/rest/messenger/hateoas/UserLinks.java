package by.rest.messenger.hateoas;

import by.rest.messenger.model.Link;
import by.rest.messenger.model.entity.User;
import by.rest.messenger.resources.UserResource;

import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

public class UserLinks {

    private UserLinks() {}

    public static List<Link> getLinks(UriInfo uriInfo, User user) {
        List<Link> links = new ArrayList<>();

        links.add(new Link(getUriForSelf(uriInfo, user.getLogin()), "self"));

        return links;
    }

    private static String getUriForSelf(UriInfo uriInfo, String login) {
        return uriInfo
                .getBaseUriBuilder()
                .path(UserResource.class)
                .path(login)
                .build()
                .toString();
    }

}
