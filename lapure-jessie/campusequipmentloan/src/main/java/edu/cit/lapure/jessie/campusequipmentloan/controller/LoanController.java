package edu.cit.lapure.jessie.campusequipmentloan.controller;

import edu.cit.lapure.jessie.campusequipmentloan.dto.LoanCreateRequest;
import edu.cit.lapure.jessie.campusequipmentloan.dto.LoanReturnResponse;
import edu.cit.lapure.jessie.campusequipmentloan.model.Loan;
import edu.cit.lapure.jessie.campusequipmentloan.service.LoanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/loans")
@Tag(name = "Loan Management", description = "APIs for managing equipment loans")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @PostMapping
    @Operation(
        summary = "Create a new loan",
        description = "Creates a new loan for a student to borrow equipment"
    )
    public ResponseEntity<Loan> createLoan(@RequestBody LoanCreateRequest request) {
        try {
            Loan loan = loanService.createLoan(request.getEquipmentId(), request.getStudentId());
            return ResponseEntity.ok(loan);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{id}/return")
    @Operation(
        summary = "Return loaned equipment",
        description = "Records the return of previously loaned equipment and calculates any penalties"
    )
    public ResponseEntity<LoanReturnResponse> returnLoan(@PathVariable Long id) {
        try {
            Loan loan = loanService.returnLoan(id);
            double penalty = loanService.calculatePenalty(id);
            
            LoanReturnResponse response = new LoanReturnResponse(
                loan.getId(),
                loan.getEquipment().getName(),
                loan.getStudent().getName(),
                penalty
            );
            
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
