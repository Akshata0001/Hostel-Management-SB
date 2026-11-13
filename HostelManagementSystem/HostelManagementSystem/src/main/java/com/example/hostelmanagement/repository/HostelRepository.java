package com.example.hostelmanagement.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.hostelmanagement.entity.HostelStudent;

@Repository
public interface HostelRepository extends JpaRepository<HostelStudent, Integer> {
    List<HostelStudent> findByPendingFeesGreaterThan(BigDecimal amount);
    List<HostelStudent> findByRoomNumber(String roomNumber);
    List<HostelStudent> findByAdmissionDateBetween(LocalDate start, LocalDate end);
}
