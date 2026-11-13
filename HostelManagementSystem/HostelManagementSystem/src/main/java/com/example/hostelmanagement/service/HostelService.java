package com.example.hostelmanagement.service;

import java.time.LocalDate;
import java.util.List;

import com.example.hostelmanagement.dto.HostelStudentDTO;

public interface HostelService {
    HostelStudentDTO addStudent(HostelStudentDTO dto);
    List<HostelStudentDTO> getAllStudents();
    HostelStudentDTO getStudentById(Integer id);
    HostelStudentDTO updateStudent(Integer id, HostelStudentDTO dto);
    void deleteStudent(Integer id);
    List<HostelStudentDTO> getStudentsWithPendingFees();
    List<HostelStudentDTO> getStudentsByRoom(String roomNo);
    List<HostelStudentDTO> getAdmissionsBetween(LocalDate start, LocalDate end);
}
