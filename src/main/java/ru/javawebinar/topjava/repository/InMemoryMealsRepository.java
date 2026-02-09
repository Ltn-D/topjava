package ru.javawebinar.topjava.repository;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static org.slf4j.LoggerFactory.getLogger;

public class InMemoryMealsRepository implements MealsRepository{
    private static final Logger log = getLogger(InMemoryMealsRepository.class);
    private final ConcurrentHashMap<Integer, Meal> meals = new ConcurrentHashMap<>();
    private final AtomicInteger generatorId = new AtomicInteger(0);

    public InMemoryMealsRepository() {
        save(new Meal(null,LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        save(new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        save(new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        save(new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        save(new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        save(new Meal(null,LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        save(new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }

    @Override
    public List<Meal> findAll() {
        List<Meal> list = new ArrayList<>(meals.values());
        return Collections.unmodifiableList(list);
    }

    @Override
     public  Meal findById(int id){
        return meals.get(id);
    }

    @Override
    public synchronized Meal save(Meal meal) {
        boolean isCreate=false;
        if (meal.getId() == null) {
            isCreate = true;
            meal.setId(generatorId.incrementAndGet());
        }
        meals.put(meal.getId(), meal);
        if (isCreate) {
            log.debug("create {} is successfully", meal);
        } else {
            log.debug("edit {} is successfully", meal);
        }
        return meal;
    }

    @Override
    public void deleteById(int id) {
        meals.remove(id);
        log.debug("delete meal with id={} is successfully",id);
    }

}
