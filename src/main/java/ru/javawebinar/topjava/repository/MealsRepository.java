package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealsRepository {

     List<Meal> findAll();

    Meal findById(Integer mealId);

    void save(Meal meal);

    void deleteByID(Integer mealId);

}
