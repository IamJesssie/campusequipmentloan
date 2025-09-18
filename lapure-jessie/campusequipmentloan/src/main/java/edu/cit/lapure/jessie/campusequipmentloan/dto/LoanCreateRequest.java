package edu.cit.lapure.jessie.campusequipmentloan.dto;

import java.time.LocalDate;

public class LoanCreateRequest {
    private Long equipmentId;
    private Long studentId;

    // Constructors
    public LoanCreateRequest() {}

    public LoanCreateRequest(Long equipmentId, Long studentId) {
        this.equipmentId = equipmentId;
        this.studentId = studentId;
    }

    // Getters and Setters
    public Long getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(Long equipmentId) {
        this.equipmentId = equipmentId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
}
