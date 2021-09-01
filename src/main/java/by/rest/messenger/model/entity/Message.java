package by.rest.messenger.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "message")
@NamedQueries(
        @NamedQuery(
                name = "Message.findAllComments",
                query = "SELECT c FROM MessageComment c WHERE c.message.id = :messageId"
        )
)
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "message_id",
            nullable = false,
            unique = true,
            updatable = false
    )
    private Long id;

    @Column(
            name = "content",
            columnDefinition = "TEXT"
    )
    private String content;

    @Column(
            name = "created",
            nullable = false
    )
    private LocalDateTime created;

    @ManyToOne
    @JoinColumn(
            name = "author",
            nullable = false
    )
    private User user;

    @OneToMany(
            mappedBy = "message",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @MapKey(name = "commentNumber")
    @ToString.Exclude
    private Map<Long, MessageComment> comments = new HashMap<>();

    public Message(Long id, String content) {
        this.id = id;
        this.content = content;
    }

    public Message(String content, User user) {
        this.content = content;
        this.user = user;
        this.created = LocalDateTime.now();
    }

    public Message(Long id, String content, User user) {
        this(content, user);
        this.id = id;
    }

    public Message(String content, LocalDateTime created, User user) {
        this.content = content;
        this.created = created;
        this.user = user;
    }

    public void addComment(MessageComment messageComment) {
        comments.put(messageComment.getCommentNumber(), messageComment);
        messageComment.setMessage(this);
    }

    public Optional<MessageComment> getComment(Long commentNumber) {
        return Optional.ofNullable(comments.get(commentNumber));
    }

    public void removeComment(MessageComment messageComment) {
        comments.remove(messageComment.getCommentNumber());
        messageComment.setMessage(null);
    }

}
