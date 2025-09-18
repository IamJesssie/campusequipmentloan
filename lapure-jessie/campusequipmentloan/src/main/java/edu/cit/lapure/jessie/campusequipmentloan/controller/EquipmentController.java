package edu.cit.lapure.jessie.campusequipmentloan.controller;

import edu.cit.lapure.jessie.campusequipmentloan.model.Equipment;
import edu.cit.lapure.jessie.campusequipmentloan.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/equipment")
@Tag(name = "Equipment Management", description = "APIs for managing equipment")
public class EquipmentController {

    @Autowired
    private LoanService loanService;

    @GetMapping("/available")
    @Operation(
        summary = "Get available equipment",
        description = "Returns a list of all equipment that is currently available for loan"
    )
    public List<Equipment> getAvailableEquipment() {
        return loanService.getAvailableEquipment();
    }
}
