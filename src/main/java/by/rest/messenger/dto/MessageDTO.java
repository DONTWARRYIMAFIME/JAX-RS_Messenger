package by.rest.messenger.dto;

import by.rest.messenger.hateoas.MessageLinks;
import by.rest.messenger.model.Link;
import by.rest.messenger.model.entity.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.ws.rs.core.UriInfo;
import java.time.LocalDateTime;
import java.util.List;

public enum MessageDTO {;
    private interface Id { @Positive Long getId(); }
    private interface Content { String getContent(); }
    private interface Created { @NotNull LocalDateTime getCreated(); }
    private interface UserLogin { @NotBlank String getUserLogin(); }
    private interface Links { List<Link> getLinks();}

    @NoArgsConstructor
    @AllArgsConstructor
    @Data public static class Request implements Content, Created, UserLogin {
        private String content;
        private LocalDateTime created;
        private String userLogin;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data public static class Response implements Id, Content, Created, UserLogin, Links {
        private Long id;
        private String content;
        private LocalDateTime created;
        private String userLogin;
        private List<Link> links;

        public Response (UriInfo uriInfo, Message message) {
            this.id = message.getId();
            this.content = message.getContent();
            this.created = message.getCreated();
            this.userLogin = message.getUser().getLogin();

            this.links = MessageLinks.getLinks(uriInfo, message);
        }
    }

}
