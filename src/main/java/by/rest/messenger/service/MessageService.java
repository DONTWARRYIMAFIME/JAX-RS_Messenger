package by.rest.messenger.service;

import by.rest.messenger.dao.MessageDAO;
import by.rest.messenger.exception.MergeException;
import by.rest.messenger.exception.MessageNotFoundException;
import by.rest.messenger.model.entity.Message;
import by.rest.messenger.utils.ObjectsMerger;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class MessageService extends BaseService {

    private final MessageDAO messageDAO;

    public MessageService() {
        this.messageDAO = new MessageDAO(entityManager);
    }

    public Message getMessageById(Long id) {
        return messageDAO
                .find(id)
                .orElseThrow(() -> new MessageNotFoundException(id));
    }

    public List<Message> getAllMessages() {
        return messageDAO.findAll();
    }

    public Message createMessage(Message message) {
        if (message.getCreated() == null) {
            message.setCreated(LocalDateTime.now());
        }

        return messageDAO.save(message);
    }

    public List<Message> findPaginated(int start, int size, List<Message> messages) {
        return messages.subList(start, start + size);
    }

    public List<Message> findByYear(int year, List<Message> messages) {
        return messages
                .stream()
                .filter((m) -> m.getCreated().getYear() == year)
                .collect(Collectors.toList());
    }

    public Message updateMessage(Message message) {
        Long id = message.getId();

        Message oldMessage = getMessageById(id);

        message = ObjectsMerger
                .merge(oldMessage, message)
                .orElseThrow(() -> new MergeException(Message.class));

        return messageDAO.update(message);
    }

    public void deleteMessage(Long id) {
        //Call this method to check if message with this id exists
        getMessageById(id);

        messageDAO.delete(id);
    }


}
