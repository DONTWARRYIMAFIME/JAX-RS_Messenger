package by.rest.messenger.dao;

import by.rest.messenger.model.entity.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserDAOTest extends BaseDAOTest {

    private static UserDAO userDAO;

    @BeforeAll
    public static void setupClass() {
        BaseDAOTest.setupClass();
        userDAO = new UserDAO(entityManager);
    }

    @AfterAll
    public static void tearDownClass() {
        BaseDAOTest.tearDownClass();
    }

    @Test
    void createUser() {
        User user = new User("myLogin", "myName", "mySurname", LocalDate.now(), "12345678");
        user = userDAO.save(user);

        assertNotNull(user);
    }

    @Test
    void findByLogin() {
        assertNotNull(userDAO.find("myLogin"));
    }

}