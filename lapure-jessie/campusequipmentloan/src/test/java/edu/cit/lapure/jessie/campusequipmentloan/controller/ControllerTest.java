package edu.cit.lapure.jessie.campusequipmentloan.controller;

import edu.cit.lapure.jessie.campusequipmentloan.dto.LoanCreateRequest;
import edu.cit.lapure.jessie.campusequipmentloan.model.Equipment;
import edu.cit.lapure.jessie.campusequipmentloan.model.Loan;
import edu.cit.lapure.jessie.campusequipmentloan.model.LoanStatus;
import edu.cit.lapure.jessie.campusequipmentloan.model.Student;
import edu.cit.lapure.jessie.campusequipmentloan.service.LoanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({LoanController.class, EquipmentController.class})
public class ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
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
    void getAvailableEquipment() throws Exception {
        // Arrange
        List<Equipment> availableEquipment = Arrays.asList(equipment);
        when(loanService.getAvailableEquipment()).thenReturn(availableEquipment);

        // Act & Assert
        mockMvc.perform(get("/api/equipment/available")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Test Equipment"))
                .andExpect(jsonPath("$[0].type").value("Test Type"))
                .andExpect(jsonPath("$[0].serialNumber").value("TST-001"))
                .andExpect(jsonPath("$[0].availability").value(true));
    }

    @Test
    void createLoan() throws Exception {
        // Arrange
        when(loanService.createLoan(any(Long.class), any(Long.class))).thenReturn(loan);

        // Act & Assert
        mockMvc.perform(post("/api/loans")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"equipmentId\":1,\"studentId\":1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.status").value("ACTIVE"));
    }

    @Test
    void returnLoan() throws Exception {
        // Arrange
        loan.setReturnDate(LocalDate.now());
        loan.setStatus(LoanStatus.RETURNED);
        when(loanService.returnLoan(anyLong())).thenReturn(loan);
        when(loanService.calculatePenalty(anyLong())).thenReturn(0.0);

        // Act & Assert
        mockMvc.perform(post("/api/loans/1/return")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.loanId").value(1))
                .andExpect(jsonPath("$.equipmentName").value("Test Equipment"))
                .andExpect(jsonPath("$.studentName").value("Test Student"))
                .andExpect(jsonPath("$.penalty").value(0.0));
    }
}
