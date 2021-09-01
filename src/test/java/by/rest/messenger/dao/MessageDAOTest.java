package by.rest.messenger.dao;

import by.rest.messenger.model.entity.Message;
import by.rest.messenger.model.entity.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MessageDAOTest extends BaseDAOTest {

    private static UserDAO userDAO;
    private static MessageDAO messageDAO;

    @BeforeAll
    public static void setupClass() {
        BaseDAOTest.setupClass();
        userDAO = new UserDAO(entityManager);
        messageDAO = new MessageDAO(entityManager);
    }

    @AfterAll
    public static void tearDownClass() {
        BaseDAOTest.tearDownClass();
    }

    @Test
    public void createMessage() {
        User user = userDAO.find("melodicLark56").orElse(null);

        Message message = new Message("My message", user);
        message = messageDAO.save(message);

        assertTrue(message.getId() > 0);
    }


}
