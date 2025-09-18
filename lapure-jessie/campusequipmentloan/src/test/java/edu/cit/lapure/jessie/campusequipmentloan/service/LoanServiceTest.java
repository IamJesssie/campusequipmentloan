package edu.cit.lapure.jessie.campusequipmentloan.service;

import edu.cit.lapure.jessie.campusequipmentloan.model.Equipment;
import edu.cit.lapure.jessie.campusequipmentloan.model.Loan;
import edu.cit.lapure.jessie.campusequipmentloan.model.LoanStatus;
import edu.cit.lapure.jessie.campusequipmentloan.model.Student;
import edu.cit.lapure.jessie.campusequipmentloan.repository.EquipmentRepository;
import edu.cit.lapure.jessie.campusequipmentloan.repository.LoanRepository;
import edu.cit.lapure.jessie.campusequipmentloan.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoanServiceTest {

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private EquipmentRepository equipmentRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private PenaltyCalculator penaltyCalculator;

    @InjectMocks
    private LoanService loanService;

    private Equipment equipment;
    private Student student;
    private Loan loan;

    @BeforeEach
    void setUp() {
        equipment = new Equipment("Test Equipment", "Test Type", "TST-001", true);
        equipment.setId(1L);

        student = new Student("S001", "Test Student", "test@example.com");
        student.setId(1L);

        loan = new Loan(equipment, student, LocalDate.now(), LocalDate.now().plusDays(7), LoanStatus.ACTIVE);
        loan.setId(1L);
    }

    @Test
    void createLoan_Success() {
        // Arrange
        when(equipmentRepository.findById(1L)).thenReturn(Optional.of(equipment));
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(loanRepository.findByStudentIdAndStatus(1L, LoanStatus.ACTIVE)).thenReturn(new ArrayList<>());
        when(loanRepository.save(any(Loan.class))).thenReturn(loan);

        // Act
        Loan result = loanService.createLoan(1L, 1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(equipment, result.getEquipment());
        assertEquals(student, result.getStudent());
        assertEquals(LoanStatus.ACTIVE, result.getStatus());
        verify(equipmentRepository).save(equipment);
        assertFalse(equipment.getAvailability());
    }

    @Test
    void createLoan_EquipmentNotAvailable() {
        // Arrange
        equipment.setAvailability(false);
        when(equipmentRepository.findById(1L)).thenReturn(Optional.of(equipment));
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> loanService.createLoan(1L, 1L));
        verify(loanRepository, never()).save(any());
    }

    @Test
    void createLoan_MaxLoansReached() {
        // Arrange
        List<Loan> activeLoans = new ArrayList<>();
        activeLoans.add(loan);
        activeLoans.add(new Loan(equipment, student, LocalDate.now(), LocalDate.now().plusDays(7), LoanStatus.ACTIVE));

        when(equipmentRepository.findById(1L)).thenReturn(Optional.of(equipment));
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(loanRepository.findByStudentIdAndStatus(1L, LoanStatus.ACTIVE)).thenReturn(activeLoans);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> loanService.createLoan(1L, 1L));
        verify(loanRepository, never()).save(any());
    }

    @Test
    void returnLoan_Success() {
        // Arrange
        when(loanRepository.findById(1L)).thenReturn(Optional.of(loan));
        when(loanRepository.save(any(Loan.class))).thenReturn(loan);

        // Act
        Loan result = loanService.returnLoan(1L);

        // Assert
        assertNotNull(result);
        assertEquals(LoanStatus.RETURNED, result.getStatus());
        assertNotNull(result.getReturnDate());
        verify(equipmentRepository).save(equipment);
        assertTrue(equipment.getAvailability());
    }

    @Test
    void returnLoan_Overdue() {
        // Arrange
        loan.setDueDate(LocalDate.now().minusDays(2));
        when(loanRepository.findById(1L)).thenReturn(Optional.of(loan));
        when(loanRepository.save(any(Loan.class))).thenReturn(loan);

        // Act
        Loan result = loanService.returnLoan(1L);

        // Assert
        assertNotNull(result);
        assertEquals(LoanStatus.OVERDUE, result.getStatus());
    }

    @Test
    void calculatePenalty_NoFee() {
        // Arrange
        loan.setReturnDate(loan.getDueDate());
        when(loanRepository.findById(1L)).thenReturn(Optional.of(loan));
        when(penaltyCalculator.calculatePenalty(any(), any())).thenReturn(0.0);

        // Act
        double penalty = loanService.calculatePenalty(1L);

        // Assert
        assertEquals(0.0, penalty);
    }

    @Test
    void calculatePenalty_WithFee() {
        // Arrange
        LocalDate dueDate = LocalDate.now().minusDays(3);
        LocalDate returnDate = LocalDate.now();
        loan.setDueDate(dueDate);
        loan.setReturnDate(returnDate);
        loan.setStatus(LoanStatus.OVERDUE);

        when(loanRepository.findById(1L)).thenReturn(Optional.of(loan));
        when(penaltyCalculator.calculatePenalty(dueDate, returnDate)).thenReturn(150.0);

        // Act
        double penalty = loanService.calculatePenalty(1L);

        // Assert
        assertEquals(150.0, penalty);
    }

    @Test
    void getAvailableEquipment() {
        // Arrange
        List<Equipment> availableEquipment = List.of(equipment);
        when(equipmentRepository.findByAvailabilityTrue()).thenReturn(availableEquipment);

        // Act
        List<Equipment> result = loanService.getAvailableEquipment();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(equipment, result.get(0));
    }
}
