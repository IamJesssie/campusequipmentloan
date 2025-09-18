package edu.cit.lapure.jessie.campusequipmentloan.service;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component
public class DailyPenaltyCalculator implements PenaltyCalculator {

    private static final double PENALTY_PER_DAY = 50.0;

    @Override
    public double calculatePenalty(LocalDate dueDate, LocalDate returnDate) {
        if (returnDate.isBefore(dueDate) || returnDate.isEqual(dueDate)) {
            return 0.0;
        }
        long daysOverdue = ChronoUnit.DAYS.between(dueDate, returnDate);
        return daysOverdue * PENALTY_PER_DAY;
    }
}
