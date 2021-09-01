package by.rest.messenger.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "message_comment")
public class MessageComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "comment_id",
            unique = true,
            updatable = false,
            nullable = false
    )
    private Long id;

    @Column(
            name = "comment_number",
            nullable = false
    )
    private Long commentNumber;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "message_id",
            nullable = false
    )
    @ToString.Exclude
    private Message message;

    public MessageComment(Long commentNumber, String content) {
        this.commentNumber = commentNumber;
        this.content = content;
    }

    public MessageComment(String content, User user) {
        this.content = content;
        this.user = user;
    }

    public MessageComment(Long commentNumber, String content, LocalDateTime created) {
        this.commentNumber = commentNumber;
        this.content = content;
        this.created = created;
    }

}
