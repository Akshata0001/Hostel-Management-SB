CREATE DATABASE IF NOT EXISTS hostel_management;
USE hostel_management;

-- TotalFees is included as an additional column because the project specification
-- requires PendingFees = TotalFees - FeesPaid, but TotalFees is absent from its
-- original list of table columns.
CREATE TABLE IF NOT EXISTS HostelStudents (
    StudentID INT PRIMARY KEY AUTO_INCREMENT,
    StudentName VARCHAR(100) NOT NULL,
    RoomNumber VARCHAR(10) NOT NULL,
    AdmissionDate DATE NOT NULL,
    TotalFees DECIMAL(10,2) NOT NULL,
    FeesPaid DECIMAL(10,2) NOT NULL,
    PendingFees DECIMAL(10,2) NOT NULL
);

-- Optional sample records for testing
INSERT INTO HostelStudents
(StudentName, RoomNumber, AdmissionDate, TotalFees, FeesPaid, PendingFees)
VALUES
('Ananya Rao', 'A-101', '2026-08-01', 50000.00, 30000.00, 20000.00),
('Sneha Patil', 'B-202', '2026-08-05', 50000.00, 50000.00, 0.00);
