package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private Map<Integer, Meal> mealsMap;

    private final Map<Integer, Map<Integer, Meal>> userMealsMap = new ConcurrentHashMap<>();

    private final AtomicInteger counter = new AtomicInteger(0);

    public static final int ADMIN_ID = 1;

    public static final int USER_ID = 2;

    {
        MealsUtil.meals.forEach(meal -> save(meal, ADMIN_ID));

        Arrays.asList(
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак User", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед User", 1000),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин User", 500)).forEach(meal -> save(meal, USER_ID));

    }

    @Override
    public Meal save(Meal meal, int userId) {
        mealsMap = userMealsMap.getOrDefault(userId, new ConcurrentHashMap<>());
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            mealsMap.put(meal.getId(), meal);
            userMealsMap.put(userId, mealsMap);
            return meal;
        }
        // handle case: update, but not present in storage
        Meal updatedMeal = mealsMap.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
        userMealsMap.put(userId, mealsMap);
        return updatedMeal;
    }

    @Override
    public boolean delete(int id, int userId) {
        mealsMap = userMealsMap.getOrDefault(userId, new ConcurrentHashMap<>());
        return mealsMap.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        mealsMap = userMealsMap.getOrDefault(userId, new ConcurrentHashMap<>());
        return mealsMap.getOrDefault(id, null);
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        mealsMap = userMealsMap.getOrDefault(userId, new ConcurrentHashMap<>());
        return mealsMap.values().stream()
                .sorted(Comparator.comparing(Meal::getDate).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<Meal> getWithDataFilter(int userId, LocalDate startDate, LocalDate endDate) {
        return filterByPredicate(getAll(userId), meal -> DateTimeUtil.isBetweenHalfOpen(meal.getDate(), startDate, endDate));
    }

    private static List<Meal> filterByPredicate(Collection<Meal> meals, Predicate<Meal> filter) {
        return meals.stream()
                .filter(filter)
                .collect(Collectors.toList());
    }
}

