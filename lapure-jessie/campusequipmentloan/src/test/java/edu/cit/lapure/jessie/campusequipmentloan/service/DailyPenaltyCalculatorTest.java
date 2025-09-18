package edu.cit.lapure.jessie.campusequipmentloan.service;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class DailyPenaltyCalculatorTest {

    private final DailyPenaltyCalculator calculator = new DailyPenaltyCalculator();

    @Test
    void calculatePenalty_ReturnedOnTime() {
        // Arrange
        LocalDate dueDate = LocalDate.now();
        LocalDate returnDate = LocalDate.now();
        
        // Act
        double penalty = calculator.calculatePenalty(dueDate, returnDate);
        
        // Assert
        assertEquals(0.0, penalty);
    }

    @Test
    void calculatePenalty_ReturnedEarly() {
        // Arrange
        LocalDate dueDate = LocalDate.now().plusDays(2);
        LocalDate returnDate = LocalDate.now();
        
        // Act
        double penalty = calculator.calculatePenalty(dueDate, returnDate);
        
        // Assert
        assertEquals(0.0, penalty);
    }

    @Test
    void calculatePenalty_OneDayLate() {
        // Arrange
        LocalDate dueDate = LocalDate.now().minusDays(1);
        LocalDate returnDate = LocalDate.now();
        
        // Act
        double penalty = calculator.calculatePenalty(dueDate, returnDate);
        
        // Assert
        assertEquals(50.0, penalty);
    }

    @Test
    void calculatePenalty_ThreeDaysLate() {
        // Arrange
        LocalDate dueDate = LocalDate.now().minusDays(3);
        LocalDate returnDate = LocalDate.now();
        
        // Act
        double penalty = calculator.calculatePenalty(dueDate, returnDate);
        
        // Assert
        assertEquals(150.0, penalty);
    }
}
