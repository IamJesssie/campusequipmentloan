package edu.cit.lapure.jessie.campusequipmentloan.service;

import java.time.LocalDate;

public interface PenaltyCalculator {
    double calculatePenalty(LocalDate dueDate, LocalDate returnDate);
}
