package com.GlaMik.VacationPayCalculator;

import java.time.LocalDate;

public class VacationPayCalculator {
    private Double averageAnnualSalary;
    private LocalDate vacationStart;
    private LocalDate vacationEnd;

    public VacationPayCalculator(Double averageAnnualSalary, LocalDate vacationStart, LocalDate vacationEnd) {
        this.averageAnnualSalary = averageAnnualSalary;
        this.vacationStart = vacationStart;
        this.vacationEnd = vacationEnd;
    }

    public double getAverageSalary() {
        return averageAnnualSalary;
    }

    public void setAverageSalary(double averageAnnualSalary) {
        this.averageAnnualSalary = averageAnnualSalary;
    }

    public double calculateVacationPay() {
        VacationDaysCalculator vacationDaysCalculator = new VacationDaysCalculator(vacationStart, vacationEnd);
        //decided not to go too deep into accounting and took formula from the internet
        final double AVERAGE_DAY_IN_MONTH = 29.3;
        return averageAnnualSalary / AVERAGE_DAY_IN_MONTH * vacationDaysCalculator.computeVacationDays();
    }

}
