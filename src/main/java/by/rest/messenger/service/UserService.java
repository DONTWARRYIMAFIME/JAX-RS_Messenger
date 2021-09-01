package by.rest.messenger.service;

import by.rest.messenger.dao.UserDAO;
import by.rest.messenger.exception.MergeException;
import by.rest.messenger.model.entity.User;
import by.rest.messenger.exception.UserDuplicationException;
import by.rest.messenger.exception.UserNotFoundException;
import by.rest.messenger.security.PasswordEncoder;
import by.rest.messenger.utils.ObjectsMerger;

import java.util.List;

public class UserService extends BaseService {

    private final UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO(entityManager);
    }

    public User getUserByLogin(String login) {
        return userDAO
                .find(login)
                .orElseThrow(() -> new UserNotFoundException(login));
    }

    public List<User> getAllUsers() {
        return userDAO.findAll();
    }

    public User createUser(User user) {
        String login = user.getLogin();

        boolean exists = userDAO
                .find(login)
                .isPresent();

        if (exists) {
            throw new UserDuplicationException(login);
        }

        user.setPassword(PasswordEncoder.generateMD5(user.getPassword()));
        return userDAO.save(user);
    }

    public User updateUser(User user) {
        String login = user.getLogin();

        User oldUser = getUserByLogin(login);

        user = ObjectsMerger
                .merge(oldUser, user)
                .orElseThrow(() -> new MergeException(User.class));

        user.setPassword(PasswordEncoder.generateMD5(user.getPassword()));
        return userDAO.update(user);
    }

    public void deleteUser(String login) {
        getUserByLogin(login);
        userDAO.delete(login);
    }

}