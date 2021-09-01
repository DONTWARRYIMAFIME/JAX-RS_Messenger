package by.rest.messenger.dao;

import by.rest.messenger.model.entity.Message;

import javax.persistence.EntityManager;

public class MessageDAO extends JpaDAO<Message, Long>{

    public MessageDAO(EntityManager entityManager) {
        super(Message.class, entityManager);
    }


}