package ru.javawebinar.topjava.repository;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static org.slf4j.LoggerFactory.getLogger;

public class InMemoryMealsRepository implements MealsRepository {
    private static final Logger log = getLogger(InMemoryMealsRepository.class);

    private final Map<Integer, Meal> mealsMap = new ConcurrentHashMap<>();
    private final AtomicInteger generatorId = new AtomicInteger(0);
    private final List<Meal> meals = new ArrayList<>();

    {
        meals.add(new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        meals.add(new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        meals.add(new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        meals.add(new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        meals.add(new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        meals.add(new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        meals.add(new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
        meals.forEach(this::save);
    }

    @Override
    public List<Meal> findAll() {
        return new ArrayList<>(mealsMap.values());
    }

    @Override
    public Meal findById(int id) {
        return mealsMap.get(id);
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.getId() == null) {
            meal.setId(generatorId.incrementAndGet());
            mealsMap.put(meal.getId(), meal);
            log.debug("create {} is successfully", meal);
            return meal;
        } else {
            Meal updatedMeal =  mealsMap.computeIfPresent(meal.getId(), (key, value) -> meal);
            log.debug("edit {} is successfully", meal);
            return updatedMeal != null ? meal : null;
        }
    }

    @Override
    public void deleteById(int id) {
        mealsMap.remove(id);
        log.debug("delete meal with id={} is successfully", id);
    }
}
