package by.rest.messenger.dto;

import by.rest.messenger.hateoas.UserLinks;
import by.rest.messenger.model.Link;
import by.rest.messenger.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.ws.rs.core.UriInfo;
import java.time.LocalDate;
import java.util.List;

public enum UserDTO {;
    private interface Login { @NotBlank String getLogin(); }
    private interface FirstName { @NotBlank String getFirstName(); }
    private interface LastName { @NotBlank String getLastName(); }
    private interface Dob { @NotNull LocalDate getDob(); }
    private interface Password { @NotBlank String getPassword(); }
    private interface Links { List<Link> getLinks();}

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Request implements Login, FirstName, LastName, Dob, Password {
        private String login;
        private String firstName;
        private String lastName;
        private LocalDate dob;
        private String password;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data public static class Response implements Login, FirstName, LastName, Dob, Links {
        private String login;
        private String firstName;
        private String lastName;
        private LocalDate dob;
        private List<Link> links;

        public Response(UriInfo uriInfo, User user) {
            this.login = user.getLogin();
            this.firstName = user.getFirstName();
            this.lastName = user.getLastName();
            this.dob = user.getDob();

            this.links = UserLinks.getLinks(uriInfo, user);
        }

    }

}