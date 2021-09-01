package by.rest.messenger.dao;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class JpaDAO<T, U> implements GenericDAO<T, U> {

    private final Class<T> type;
    private final EntityManager entityManager;

    public JpaDAO(Class<T> type, EntityManager entityManager) {
        this.type = type;
        this.entityManager = entityManager;
    }

    @Override
    public T save(T entity) {
        entityManager.getTransaction().begin();

        entityManager.persist(entity);
        entityManager.flush();
        entityManager.refresh(entity);

        entityManager.getTransaction().commit();

        return entity;
    }

    @Override
    public T update(T entity) {
        entityManager.getTransaction().begin();

        entity = entityManager.merge(entity);
        entityManager.getTransaction().commit();

        return entity;
    }

    @Override
    public Optional<T> find(U id) {
        return Optional.ofNullable(entityManager.find(type, id));
    }

    @Override
    public List<T> findAll() {
        Query query = entityManager.createQuery("SELECT t FROM " + type.getName() + " t");
        return query.getResultList();
    }

    @Override
    public void delete(U id) {
        entityManager.getTransaction().begin();

        Object reference = entityManager.getReference(type, id);
        entityManager.remove(reference);

        entityManager.getTransaction().commit();
    }

    @Override
    public long count() {
        Query query = entityManager.createQuery("SELECT Count(t) FROM " + type.getName() + " t");
        return (long)query.getSingleResult();
    }

    public Stream<T> findWithQueryName(String queryName) {
        Query query = entityManager.createNamedQuery(queryName);
        return query.getResultStream();
    }

    public Stream<T> findWithQueryName(String queryName, Map<String, Object> parameters) {
        Query query = entityManager.createNamedQuery(queryName);

        for (Map.Entry<String, Object> entry : parameters.entrySet())
            query.setParameter(entry.getKey(), entry.getValue());

        return query.getResultStream();
    }



}