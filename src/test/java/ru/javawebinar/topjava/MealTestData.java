package ru.javawebinar.topjava;

import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.Util;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.UserTestData.GUEST_ID;

public class MealTestData {
    public static final LocalDateTime START_DATE_TIME = LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0);
    public static final LocalDateTime END_START_DATE_TIME = LocalDateTime.of(2020, Month.JANUARY, 31, 23, 59);

    public static final Meal userMeal1 = new Meal(GUEST_ID + 1, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);
    public static final Meal userMeal2 = new Meal(GUEST_ID + 2, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000);
    public static final Meal userMeal3 = new Meal(GUEST_ID + 3, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500);
    public static final Meal userMeal4 = new Meal(GUEST_ID + 4, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100);
    public static final Meal userMeal5 = new Meal(GUEST_ID + 5, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000);
    public static final Meal userMeal6 = new Meal(GUEST_ID + 6, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500);
    public static final Meal userMeal7 = new Meal(GUEST_ID + 7, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410);
    public static final Meal adminMeal1 = new Meal(GUEST_ID + 9, LocalDateTime.of(2015, Month.JUNE, 1, 21, 0), "Админ ужин", 1500);
    public static final Meal adminMeal2 = new Meal(GUEST_ID + 8, LocalDateTime.of(2015, Month.JUNE, 1, 14, 0), "Админ ланч", 510);

    public static List<Meal> expectedUsersMeals = Stream.of(userMeal1, userMeal2, userMeal3, userMeal4, userMeal5, userMeal6, userMeal7)
            .sorted(Comparator.comparing(Meal::getDateTime).reversed())
            .collect(Collectors.toList());
    public static List<Meal> expectedAdminsMeals = Arrays.asList(adminMeal1, adminMeal2);
    public static List<Meal> expectedFilteredMeals = expectedUsersMeals.stream()
            .filter(meal -> Util.isBetweenHalfOpen(meal.getDateTime(),START_DATE_TIME, END_START_DATE_TIME ))
            .sorted(Comparator.comparing(Meal::getDateTime).reversed())
            .collect(Collectors.toList());

    public static Meal getNewMeal() {
        return new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "Новая еда",500);
    }

    public static Meal getUpdatedMeal() {
        Meal updated = new Meal(userMeal3);
        updated.setDateTime(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        updated.setDescription("Обновленная еда");
        updated.setCalories(1000);
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual,  (Arrays.asList(expected)));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparator(configuration).isEqualTo(expected);

    }

    static RecursiveComparisonConfiguration configuration = RecursiveComparisonConfiguration.builder()
            .build();
}
