package com.GlaMik.VacationPayCalculator;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * @author Mikhail Gladkikh
 * Created: 22-09-2024
 * Description: This class computes duration of a vacation, taking into account holidays and weekends
 */
public class VacationDaysCalculator {

    private final LocalDate vacationStart;
    private final LocalDate vacationEnd;
    private Set<LocalDate> nonWorkingDays;

    private static final Map<LocalDate, Integer> HOLIDAYS = new HashMap<>();
    static {
        HOLIDAYS.put(LocalDate.of(2024, 1, 1), 8);  // Новогодние каникулы (8 дней)
        HOLIDAYS.put(LocalDate.of(2024, 2, 23), 1); // День защитника Отечества (1 день)
        HOLIDAYS.put(LocalDate.of(2024, 3, 8), 1);  // Международный женский день (1 день)
        HOLIDAYS.put(LocalDate.of(2024, 5, 1), 1);  // Праздник Весны и Труда (1 день)
        HOLIDAYS.put(LocalDate.of(2024, 5, 9), 1);  // День Победы (1 день)
        HOLIDAYS.put(LocalDate.of(2024, 6, 12), 1); // День России (1 день)
        HOLIDAYS.put(LocalDate.of(2024, 11, 4), 1); // День народного единства (1 день)
    }

    public VacationDaysCalculator(LocalDate vacationStart, LocalDate vacationEnd) {
        this.vacationStart = vacationStart;
        this.vacationEnd = vacationEnd;
    }

    public int computeVacationDays() {
        nonWorkingDays = new HashSet<>();
        computeHolidays();
        computeWeekends();

        long totalDays = DAYS.between(vacationStart, vacationEnd.plusDays((1)));
        return (int) (totalDays - nonWorkingDays.size());
    }

    public void computeHolidays() {
        for (Map.Entry<LocalDate, Integer> entry : HOLIDAYS.entrySet()) {
            LocalDate holidayStart = entry.getKey();
            int holidayDuration = entry.getValue();

            if (holidayStart.isEqual(vacationStart) ||
                    (holidayStart.isAfter(vacationStart) && holidayStart.isBefore(vacationEnd))) {
                for (int i = 0; i < holidayDuration; i++) {
                    nonWorkingDays.add(holidayStart.plusDays(i));
                }
            }
        }
    }

    public void computeWeekends() {
        LocalDate date = vacationStart;
        while (!date.isAfter(vacationEnd)) {
            if (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY) {
                nonWorkingDays.add(date);
            }
            date = date.plusDays(1);
        }
    }

}
