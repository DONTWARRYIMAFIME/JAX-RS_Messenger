package by.rest.messenger.resources;

import by.rest.messenger.dto.UserDTO;
import by.rest.messenger.model.entity.User;
import by.rest.messenger.service.UserService;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private final UserService userService = new UserService();

    @GET
    public List<UserDTO.Response> getUsers(@Context UriInfo uriInfo) {
        return userService
                .getAllUsers()
                .stream()
                .map((u) -> new UserDTO.Response(uriInfo, u))
                .collect(Collectors.toList());
    }

    @GET
    @Path("/{login}")
    public UserDTO.Response getUser(@PathParam("login") String login,
                                    @Context UriInfo uriInfo) {
        User user = userService.getUserByLogin(login);
        return new UserDTO.Response(uriInfo, user);
    }

    @POST
    public Response createUser(User user,
                               @Context UriInfo uriInfo) {
        user = userService.createUser(user);

        URI userUri = uriInfo
                .getAbsolutePathBuilder()
                .path(user.getLogin())
                .build();

        return Response
                .created(userUri)
                .entity(new UserDTO.Response(uriInfo, user))
                .build();
    }

    @PUT
    @Path("/{login}")
    public UserDTO.Response updateUser(@PathParam("login") String login,
                                       User user,
                                       @Context UriInfo uriInfo) {
        user.setLogin(login);
        user = userService.updateUser(user);

        return new UserDTO.Response(uriInfo, user);
    }

    @DELETE
    @Path("/{login}")
    public void deleteUser(@PathParam("login") String login) {
        userService.deleteUser(login);
    }

}
