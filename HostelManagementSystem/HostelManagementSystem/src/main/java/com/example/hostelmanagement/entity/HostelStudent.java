package com.example.hostelmanagement.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "HostelStudents")
public class HostelStudent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "StudentID")
    private Integer studentId;

    @Column(name = "StudentName", nullable = false, length = 100)
    private String studentName;

    @Column(name = "RoomNumber", nullable = false, length = 10)
    private String roomNumber;

    @Column(name = "AdmissionDate", nullable = false)
    private LocalDate admissionDate;

    // Added because the specification requires PendingFees = TotalFees - FeesPaid.
    @Column(name = "TotalFees", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalFees;

    @Column(name = "FeesPaid", nullable = false, precision = 10, scale = 2)
    private BigDecimal feesPaid;

    @Column(name = "PendingFees", nullable = false, precision = 10, scale = 2)
    private BigDecimal pendingFees;

    public HostelStudent() {
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
