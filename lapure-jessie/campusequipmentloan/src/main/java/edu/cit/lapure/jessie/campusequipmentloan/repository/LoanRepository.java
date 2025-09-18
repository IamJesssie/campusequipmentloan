package edu.cit.lapure.jessie.campusequipmentloan.repository;

import edu.cit.lapure.jessie.campusequipmentloan.model.Loan;
import edu.cit.lapure.jessie.campusequipmentloan.model.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByStudentIdAndStatus(Long studentId, LoanStatus status);
    List<Loan> findByStatus(LoanStatus status);
}
