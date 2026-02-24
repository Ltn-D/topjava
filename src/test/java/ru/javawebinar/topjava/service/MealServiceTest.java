package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {
    static {
        // Only for postgres driver logging
        //Remove the default console handlers in JUL
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(GUEST_ID + 7, USER_ID);
        assertMatch(meal, userMeal7);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND,USER_ID));
    }

    @Test
    public void getAnotherUserMeal() {
        assertThrows(NotFoundException.class, () -> service.get(adminMeal1.getId(),USER_ID));
    }

    @Test
    public void delete() {
        service.delete(userMeal7.getId(),USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(userMeal7.getId(),USER_ID));
    }

    @Test
    public void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND, USER_ID));
    }

    @Test
    public void deletedAnotherUserMeal() {
        assertThrows(NotFoundException.class, () -> service.delete(adminMeal1.getId(),USER_ID));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> filteredMeals = service.getBetweenInclusive(START_DATE_TIME.toLocalDate(), END_START_DATE_TIME.toLocalDate(), USER_ID);
        assertMatch(filteredMeals,expectedFilteredMeals);
    }

    @Test
    public void getAll() {
        List<Meal> userMeals = service.getAll(USER_ID);
        List<Meal> adminMeals = service.getAll(ADMIN_ID);
        assertMatch(userMeals, expectedUsersMeals);
        assertMatch(adminMeals, expectedAdminsMeals);
    }

    @Test
    public void update() {
        Meal updated = getUpdatedMeal();
        service.update(updated, USER_ID);
        assertMatch(service.get(getUpdatedMeal().getId(),USER_ID),getUpdatedMeal());
    }

    @Test
    public void updateAnotherUserMeal() {
        Meal updated = getUpdatedMeal();
        assertThrows(NotFoundException.class, () -> service.update(updated, ADMIN_ID));
    }

    @Test
    public void create() {
        Meal created = service.create(getNewMeal(), ADMIN_ID);
        Integer newId = created.getId();
        Meal newMeal = getNewMeal();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
        assertMatch(service.get(newId,ADMIN_ID), newMeal);
    }

    @Test
    public void duplicateDateTimeCreate(){
        Meal duplicate = getNewMeal();
        duplicate.setDateTime(userMeal7.getDateTime());
        assertThrows(DataAccessException.class, () -> service.create(duplicate, USER_ID));
    }
}