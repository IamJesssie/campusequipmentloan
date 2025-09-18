package edu.cit.lapure.jessie.campusequipmentloan.dto;

public class LoanReturnResponse {
    private Long loanId;
    private String equipmentName;
    private String studentName;
    private double penalty;

    // Constructors
    public LoanReturnResponse() {}

    public LoanReturnResponse(Long loanId, String equipmentName, String studentName, double penalty) {
        this.loanId = loanId;
        this.equipmentName = equipmentName;
        this.studentName = studentName;
        this.penalty = penalty;
    }

    // Getters and Setters
    public Long getLoanId() {
        return loanId;
    }

    public void setLoanId(Long loanId) {
        this.loanId = loanId;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public double getPenalty() {
        return penalty;
    }

    public void setPenalty(double penalty) {
        this.penalty = penalty;
    }
}
