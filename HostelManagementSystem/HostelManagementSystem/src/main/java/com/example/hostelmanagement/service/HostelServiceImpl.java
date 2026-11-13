package com.example.hostelmanagement.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.hostelmanagement.dto.HostelStudentDTO;
import com.example.hostelmanagement.entity.HostelStudent;
import com.example.hostelmanagement.exception.ResourceNotFoundException;
import com.example.hostelmanagement.repository.HostelRepository;

@Service
@Transactional
public class HostelServiceImpl implements HostelService {

    private final HostelRepository hostelRepository;

    public HostelServiceImpl(HostelRepository hostelRepository) {
        this.hostelRepository = hostelRepository;
    }

    @Override
    public HostelStudentDTO addStudent(HostelStudentDTO dto) {
        HostelStudent student = toEntity(dto);
        calculatePendingFees(student);
        return toDTO(hostelRepository.save(student));
    }

    @Override
    @Transactional(readOnly = true)
    public List<HostelStudentDTO> getAllStudents() {
        return hostelRepository.findAll().stream().map(this::toDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public HostelStudentDTO getStudentById(Integer id) {
        return toDTO(findStudent(id));
    }

    @Override
    public HostelStudentDTO updateStudent(Integer id, HostelStudentDTO dto) {
        HostelStudent existing = findStudent(id);
        existing.setStudentName(dto.getStudentName());
        existing.setRoomNumber(dto.getRoomNumber());
        existing.setAdmissionDate(dto.getAdmissionDate());
        existing.setTotalFees(dto.getTotalFees());
        existing.setFeesPaid(dto.getFeesPaid());
        calculatePendingFees(existing);
        return toDTO(hostelRepository.save(existing));
    }

    @Override
    public void deleteStudent(Integer id) {
        HostelStudent student = findStudent(id);
        hostelRepository.delete(student);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HostelStudentDTO> getStudentsWithPendingFees() {
        return hostelRepository.findByPendingFeesGreaterThan(BigDecimal.ZERO)
                .stream().map(this::toDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<HostelStudentDTO> getStudentsByRoom(String roomNo) {
        return hostelRepository.findByRoomNumber(roomNo)
                .stream().map(this::toDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<HostelStudentDTO> getAdmissionsBetween(LocalDate start, LocalDate end) {
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Start date must be before or equal to end date");
        }
        return hostelRepository.findByAdmissionDateBetween(start, end)
                .stream().map(this::toDTO).toList();
    }

    private HostelStudent findStudent(Integer id) {
        return hostelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hostel student not found with ID: " + id));
    }

    private void calculatePendingFees(HostelStudent student) {
        if (student.getFeesPaid().compareTo(student.getTotalFees()) > 0) {
            throw new IllegalArgumentException("Fees paid cannot be greater than total fees");
        }
        student.setPendingFees(student.getTotalFees().subtract(student.getFeesPaid()));
    }

    private HostelStudent toEntity(HostelStudentDTO dto) {
        HostelStudent student = new HostelStudent();
        student.setStudentName(dto.getStudentName().trim());
        student.setRoomNumber(dto.getRoomNumber().trim());
        student.setAdmissionDate(dto.getAdmissionDate());
        student.setTotalFees(dto.getTotalFees());
        student.setFeesPaid(dto.getFeesPaid());
        return student;
    }

    private HostelStudentDTO toDTO(HostelStudent student) {
        HostelStudentDTO dto = new HostelStudentDTO();
        dto.setStudentId(student.getStudentId());
        dto.setStudentName(student.getStudentName());
        dto.setRoomNumber(student.getRoomNumber());
        dto.setAdmissionDate(student.getAdmissionDate());
        dto.setTotalFees(student.getTotalFees());
        dto.setFeesPaid(student.getFeesPaid());
        dto.setPendingFees(student.getPendingFees());
        return dto;
    }
}
