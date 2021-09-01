package by.rest.messenger.dao;

import by.rest.messenger.model.entity.User;

import javax.persistence.EntityManager;

public class UserDAO extends JpaDAO<User, String>{

    public UserDAO(EntityManager entityManager) {
        super(User.class, entityManager);
    }

}