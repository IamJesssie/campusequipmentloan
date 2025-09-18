package edu.cit.lapure.jessie.campusequipmentloan.service;

import edu.cit.lapure.jessie.campusequipmentloan.model.*;
import edu.cit.lapure.jessie.campusequipmentloan.repository.EquipmentRepository;
import edu.cit.lapure.jessie.campusequipmentloan.repository.LoanRepository;
import edu.cit.lapure.jessie.campusequipmentloan.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private PenaltyCalculator penaltyCalculator;

    private static final int MAX_ACTIVE_LOANS = 2;
    private static final int LOAN_DURATION_DAYS = 7;

    @Transactional
    public Loan createLoan(Long equipmentId, Long studentId) {
        Equipment equipment = equipmentRepository.findById(equipmentId)
                .orElseThrow(() -> new IllegalArgumentException("Equipment not found"));
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));

        if (!equipment.getAvailability()) {
            throw new IllegalArgumentException("Equipment is not available");
        }

        List<Loan> activeLoans = loanRepository.findByStudentIdAndStatus(studentId, LoanStatus.ACTIVE);
        if (activeLoans.size() >= MAX_ACTIVE_LOANS) {
            throw new IllegalArgumentException("Student has reached maximum active loans");
        }

        LocalDate startDate = LocalDate.now();
        LocalDate dueDate = startDate.plusDays(LOAN_DURATION_DAYS);

        Loan loan = new Loan(equipment, student, startDate, dueDate, LoanStatus.ACTIVE);
        loan = loanRepository.save(loan);

        equipment.setAvailability(false);
        equipmentRepository.save(equipment);

        return loan;
    }

    @Transactional
    public Loan returnLoan(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new IllegalArgumentException("Loan not found"));

        if (loan.getStatus() != LoanStatus.ACTIVE) {
            throw new IllegalArgumentException("Loan is not active");
        }

        LocalDate returnDate = LocalDate.now();
        loan.setReturnDate(returnDate);

        if (returnDate.isAfter(loan.getDueDate())) {
            loan.setStatus(LoanStatus.OVERDUE);
        } else {
            loan.setStatus(LoanStatus.RETURNED);
        }

        loan = loanRepository.save(loan);

        Equipment equipment = loan.getEquipment();
        equipment.setAvailability(true);
        equipmentRepository.save(equipment);

        return loan;
    }

    public double calculatePenalty(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new IllegalArgumentException("Loan not found"));

        if (loan.getReturnDate() == null) {
            return 0.0;
        }

        return penaltyCalculator.calculatePenalty(loan.getDueDate(), loan.getReturnDate());
    }

    public List<Equipment> getAvailableEquipment() {
        return equipmentRepository.findByAvailabilityTrue();
    }
}
