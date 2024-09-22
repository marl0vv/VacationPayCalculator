package com.GlaMik.VacationPayCalculator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@RestController
@RequestMapping("/vacationPay")
public class VacationPayCalculatorController {

    private static final Logger logger = LoggerFactory.getLogger(VacationPayCalculatorController.class);

    @GetMapping("/{averageSalary}/{vacationStartString}/{vacationEndString}")
    private ResponseEntity<Double> getVacationPay(@PathVariable Double averageSalary,
                                                  @PathVariable String vacationStartString,
                                                  @PathVariable String vacationEndString) {
        try {
            LocalDate vacationStart = LocalDate.parse(vacationStartString);
            LocalDate vacationEnd = LocalDate.parse(vacationEndString);

            if (vacationStart.isAfter(vacationEnd)) {
                logger.error("Vacation start date cannot be after end date.");
                return ResponseEntity.badRequest().build();
            }

            VacationPayCalculator vacationPayCalculator = new VacationPayCalculator(averageSalary, vacationStart, vacationEnd);
            double vacationPay = vacationPayCalculator.calculateVacationPay();
            double vacationPayRounded = Math.round(vacationPay * 100.0) / 100.0;

            return ResponseEntity.ok(vacationPayRounded);

        } catch (DateTimeParseException e) {
            logger.error("Invalid date format", e);
            return ResponseEntity.badRequest().build();
        }
    }
}
