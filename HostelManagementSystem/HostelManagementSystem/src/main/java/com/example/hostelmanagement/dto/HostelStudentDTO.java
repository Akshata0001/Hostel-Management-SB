package com.example.hostelmanagement.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class HostelStudentDTO {

    private Integer studentId;

    @NotBlank(message = "Student name is required")
    private String studentName;

    @NotBlank(message = "Room number is required")
    @Pattern(regexp = "^[A-Za-z0-9-]{1,10}$",
             message = "Room number may contain only letters, numbers, and hyphens (maximum 10 characters)")
    private String roomNumber;

    @NotNull(message = "Admission date is required")
    private LocalDate admissionDate;

    @NotNull(message = "Total fees is required")
    @DecimalMin(value = "0.01", message = "Total fees must be greater than 0")
    private BigDecimal totalFees;

    @NotNull(message = "Fees paid is required")
    @DecimalMin(value = "0.00", message = "Fees paid cannot be negative")
    private BigDecimal feesPaid;

    private BigDecimal pendingFees;

    public HostelStudentDTO() {
    }

    public Integer getStudentId() { return studentId; }
    public void setStudentId(Integer studentId) { this.studentId = studentId; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }

    public LocalDate getAdmissionDate() { return admissionDate; }
    public void setAdmissionDate(LocalDate admissionDate) { this.admissionDate = admissionDate; }

    public BigDecimal getTotalFees() { return totalFees; }
    public void setTotalFees(BigDecimal totalFees) { this.totalFees = totalFees; }

    public BigDecimal getFeesPaid() { return feesPaid; }
    public void setFeesPaid(BigDecimal feesPaid) { this.feesPaid = feesPaid; }

    public BigDecimal getPendingFees() { return pendingFees; }
    public void setPendingFees(BigDecimal pendingFees) { this.pendingFees = pendingFees; }
}
