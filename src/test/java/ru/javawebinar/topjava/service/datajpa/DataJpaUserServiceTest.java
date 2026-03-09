package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaUserServiceTest extends AbstractUserServiceTest {

    @Test
    public void getWithMeals() {
        User actual = service.getWithMeals(ADMIN_ID);
        USER_MATCHER.assertMatch(actual, admin);
        MEAL_MATCHER.assertMatch(actual.getMeals(), MealTestData.adminMeal1, MealTestData.adminMeal2);
    }

    @Test
    public void getNotFoundMeals() {
        User actual = service.getWithMeals(GUEST_ID);
        USER_MATCHER.assertMatch(actual, guest);
        assertThat(actual.getMeals()).isEmpty();
    }
}
