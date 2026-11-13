package com.example.hostelmanagement.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.hostelmanagement.dto.HostelStudentDTO;
import com.example.hostelmanagement.service.HostelService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/hostel-students")
public class HostelController {

    private final HostelService hostelService;

    public HostelController(HostelService hostelService) {
        this.hostelService = hostelService;
    }

    @PostMapping
    public ResponseEntity<HostelStudentDTO> addStudent(@Valid @RequestBody HostelStudentDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(hostelService.addStudent(dto));
    }

    @GetMapping
    public ResponseEntity<List<HostelStudentDTO>> getAllStudents() {
        return ResponseEntity.ok(hostelService.getAllStudents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HostelStudentDTO> getStudentById(@PathVariable Integer id) {
        return ResponseEntity.ok(hostelService.getStudentById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HostelStudentDTO> updateStudent(
            @PathVariable Integer id, @Valid @RequestBody HostelStudentDTO dto) {
        return ResponseEntity.ok(hostelService.updateStudent(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Integer id) {
        hostelService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/reports/pending-fees")
    public ResponseEntity<List<HostelStudentDTO>> getPendingFeesStudents() {
        return ResponseEntity.ok(hostelService.getStudentsWithPendingFees());
    }

    @GetMapping("/reports/room")
    public ResponseEntity<List<HostelStudentDTO>> getStudentsByRoom(@RequestParam String roomNo) {
        return ResponseEntity.ok(hostelService.getStudentsByRoom(roomNo));
    }

    @GetMapping("/reports/admissions")
    public ResponseEntity<List<HostelStudentDTO>> getAdmissionsBetween(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        return ResponseEntity.ok(hostelService.getAdmissionsBetween(start, end));
    }
}
