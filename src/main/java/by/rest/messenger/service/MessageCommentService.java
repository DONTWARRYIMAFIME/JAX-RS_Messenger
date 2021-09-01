package by.rest.messenger.service;

import by.rest.messenger.dao.MessageDAO;
import by.rest.messenger.exception.CommentNotFoundException;
import by.rest.messenger.exception.MergeException;
import by.rest.messenger.exception.MessageNotFoundException;
import by.rest.messenger.model.entity.MessageComment;
import by.rest.messenger.model.entity.Message;
import by.rest.messenger.utils.ObjectsMerger;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MessageCommentService extends BaseService {

    private final MessageDAO messageDAO;

    public MessageCommentService() {
        this.messageDAO = new MessageDAO(entityManager);
    }

    public List<MessageComment> getAllComments(Long messageId) {
        return new ArrayList<>(messageDAO
                .find(messageId)
                .orElseThrow(() -> new MessageNotFoundException(messageId))
                .getComments()
                .values());
    }

    public MessageComment getComment(Long messageId, Long commentNumber) {
        return messageDAO
                .find(messageId)
                .orElseThrow(() -> new MessageNotFoundException(messageId))
                .getComment(commentNumber)
                .orElseThrow(() -> new CommentNotFoundException(commentNumber));
    }

    public MessageComment createComment(Long messageId, MessageComment comment) {
        Message message = messageDAO
                .find(messageId)
                .orElseThrow(() -> new MessageNotFoundException(messageId));

        //This check allows us to create message with custom creation time
        if (comment.getCreated() == null) {
            comment.setCreated(LocalDateTime.now());
        }

        Long commentNumber = calculateCommentNumber(message);

        comment.setCommentNumber(commentNumber);
        message.addComment(comment);

        message = messageDAO.update(message);
        comment = message.getComments().get(commentNumber);

        return comment;
    }

    public MessageComment updateComment(Long messageId, MessageComment comment) {
        Long commentNumber = comment.getCommentNumber();
        MessageComment oldComment = getComment(messageId, commentNumber);
        Message message = oldComment.getMessage();

        comment = ObjectsMerger
                .merge(oldComment, comment)
                .orElseThrow(() -> new MergeException(MessageComment.class));

        //Updating comment
        message.addComment(comment);
        messageDAO.update(message);

        return comment;
    }

    public void deleteComment(Long messageId, Long commentNumber) {
        MessageComment comment = getComment(messageId, commentNumber);
        Message message = comment.getMessage();

        message.removeComment(comment);

        messageDAO.update(message);
    }

    //Gets current max number and returns it + 1
    private Long calculateCommentNumber(Message message) {
        return message
                .getComments()
                .keySet()
                .stream()
                .mapToLong((k) -> k)
                .max()
                .orElse(0L) + 1L;
    }


}
