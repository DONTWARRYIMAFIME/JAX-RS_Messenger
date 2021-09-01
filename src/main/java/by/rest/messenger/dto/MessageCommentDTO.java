package by.rest.messenger.dto;

import by.rest.messenger.hateoas.MessageCommentLinks;
import by.rest.messenger.model.Link;
import by.rest.messenger.model.entity.MessageComment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.ws.rs.core.UriInfo;
import java.time.LocalDateTime;
import java.util.List;

public class MessageCommentDTO {
    private interface Id { @Positive Long getId(); }
    private interface CommentNumber { @Positive Long getCommentNumber(); }
    private interface Content { String getContent(); }
    private interface Created { @NotNull LocalDateTime getCreated(); }
    private interface Author { @NotBlank String getAuthor(); }
    private interface MessageId { @Positive Long getMessageId(); }
    private interface Links { List<Link> getLinks();}

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Request implements Content, Author, Created {
        private String content;
        private String author;
        private LocalDateTime created;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data public static class Response implements Id, CommentNumber, Content, Created, Author, MessageId, Links {
        private Long id;
        private Long commentNumber;
        private String content;
        private LocalDateTime created;
        private String author;
        private Long messageId;
        private List<Link> links;

        public Response (UriInfo uriInfo, MessageComment comment) {
            this.id = comment.getId();
            this.commentNumber = comment.getCommentNumber();
            this.content = comment.getContent();
            this.created = comment.getCreated();
            this.author = comment.getUser().getLogin();
            this.messageId = comment.getMessage().getId();

            this.links = MessageCommentLinks.getLinks(uriInfo, comment);
        }
    }

}
