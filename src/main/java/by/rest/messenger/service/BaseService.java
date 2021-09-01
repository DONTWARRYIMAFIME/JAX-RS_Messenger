package by.rest.messenger.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class BaseService {

    private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Messenger");
    protected final EntityManager entityManager = entityManagerFactory.createEntityManager();


}
