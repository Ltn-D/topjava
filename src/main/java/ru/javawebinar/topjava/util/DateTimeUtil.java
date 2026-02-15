package ru.javawebinar.topjava.util;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static <T extends Comparable<T>> boolean isBetweenHalfOpen(@NotNull T ldt, T start, T end) {
        return ldt.compareTo(start) >= 0 && ldt.compareTo(end) < 0;
    }

    public static @NotNull String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }

    /**
     * Parse String from Parameter startDate in LocalDate
     *
     * @param startDataString - string from parameter
     * @return startDataString == "" -> LocalDate.MIN||LocalDate
     */

    public static LocalDate getStartDate(@NotNull String startDataString) {
        return (startDataString.isEmpty()) ? LocalDate.MIN : LocalDate.parse(startDataString);
    }

    /**
     * Parse String from Parameter endDate in LocalDate
     *
     * @param endDataString - string from parameter
     * @return endDataString == "" -> LocalDate.MAX||LocalDate plus 1 day (ldt.compareTo(end) <= 0 for day)
     */
    public static LocalDate getEndDate(@NotNull String endDataString) {
        return (endDataString.isEmpty()) ? LocalDate.MAX : LocalDate.parse(endDataString).plusDays(1);
    }

    /**
     * Parse String from Parameter startTime in LocalTime
     *
     * @param startTimeString - string from parameter
     * @return endDataString == "" -> LocalTime.MIN || LocalTime
     */
    public static LocalTime getStartTime(@NotNull String startTimeString) {
        return (startTimeString.isEmpty()) ? LocalTime.MIN : LocalTime.parse(startTimeString);
    }

    /**
     * Parse String from Parameter endTime in LocalTime
     *
     * @param endTimeString - string from parameter
     * @return endTimeString == "" -> LocalTime.MAX || LocalTime
     */
    public static LocalTime getEndTime(@NotNull String endTimeString) {
        return (endTimeString.isEmpty()) ? LocalTime.MAX : LocalTime.parse(endTimeString);
    }

}
