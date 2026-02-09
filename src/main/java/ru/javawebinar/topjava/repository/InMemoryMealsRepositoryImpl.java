package ru.javawebinar.topjava.repository;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

import static org.slf4j.LoggerFactory.getLogger;

public class InMemoryMealsRepositoryImpl implements MealsRepository{
    private static final Logger log = getLogger(InMemoryMealsRepositoryImpl.class);
    private final List<Meal> meals = Collections.synchronizedList(new LinkedList<>());

    public InMemoryMealsRepositoryImpl() {
        meals.add(new Meal(1,LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        meals.add(new Meal(2, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        meals.add(new Meal(3, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        meals.add(new Meal(4, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        meals.add(new Meal(5, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        meals.add(new Meal(6,LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        meals.add(new Meal(7, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }

    @Override
    public List<Meal> findAll() {
        return meals;
    }

    @Override
     public  Meal findById(Integer mealId){
        return meals.stream()
                .filter(meal -> Objects.equals(mealId, meal.getId()))
                .findFirst().get();
    }

    @Override
    public void save(Meal meal) {
        if (meal.getId() == null) {
            meal.setId(meals.stream()
                    .max(Comparator.comparingInt(Meal::getId))
                    .map(Meal::getId)
                    .orElse(0) + 1);
            meals.add(meal);
            log.debug("create meal is successfully");
        } else {
            Meal updatedMeal = findById(meal.getId());
            updatedMeal.setDateTime(meal.getDateTime());
            updatedMeal.setDescription((meal.getDescription()));
            updatedMeal.setCalories((meal.getCalories()));
            log.debug("edit meal is successfully");
        }
    }

    @Override
    public void deleteByID(Integer mealId) {
        meals.removeIf(meal -> Objects.equals(mealId, meal.getId()));
        log.debug("delete meal is successfully");
    }

}
