package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkIsNew;

@Controller
public class MealRestController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public List<MealTo> getAll() {
        int userId = SecurityUtil.authUserId();
        log.info("getAll");
        return service.getAll(userId);
    }

    public List<MealTo> getWithFilter(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        int userId = SecurityUtil.authUserId();
        log.info("getWithFilter:\nstartDate =  {},\n" +
                "endDate =  {},\n" +
                "startTime=  {},\n" +
                "endTime=  {},\n", startDate, endDate, startTime, endTime);
        return service.getWithFilter(userId, startDate, endDate, startTime, endTime);
    }

    public Meal get(int id) {
        int userId = SecurityUtil.authUserId();
        log.info("get {}", id);
        return service.get(id, userId);
    }

    public Meal create(Meal meal) {
        int userId = SecurityUtil.authUserId();
        log.info("create {}", meal);
        checkIsNew(meal);
        return service.create(meal, userId);
    }

    public void delete(int id) {
        int userId = SecurityUtil.authUserId();
        log.info("delete {}", id);
        service.delete(id, userId);
    }

    public void update(Meal meal, int id) {
        int userId = SecurityUtil.authUserId();
        log.info("update {} with id={}", meal, id);
        assureIdConsistent(meal, id);
        service.update(meal, userId);
    }
}