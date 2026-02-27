package ru.javawebinar.topjava.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaMealRepository implements MealRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setUser(em.find(User.class, userId));
            em.persist(meal);
        } else {
            Meal updatedMeal = em.find(Meal.class, meal.id());
            int updatedUserId = updatedMeal.getUser().getId();
            if (updatedUserId == userId) {
                meal.setUser(em.find(User.class, userId));
                em.merge(meal);
            } else {
                return null;
            }
        }
        return meal;
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        Query query = em.createNamedQuery(Meal.DELETE);
        query.setParameter("id", id).setParameter("userId", userId);
        return query.executeUpdate() != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        Query query = em.createNamedQuery(Meal.ONE, Meal.class);
        query.setParameter("id", id).setParameter("userId", userId);
        return (Meal) query.getSingleResult();
    }

    @Override
    public List<Meal> getAll(int userId) {
        Query query = em.createNamedQuery(Meal.ALL_SORTED, Meal.class);
        return query.setParameter("userId", userId).getResultList();
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        Query query = em.createNamedQuery(Meal.FILTERED, Meal.class);
        query.setParameter("userId", userId).setParameter("startDateTime", startDateTime).setParameter("endDateTime", endDateTime);
        return query.getResultList();
    }
}