# Hostel Management System – Spring Boot REST API

**Course:** EAD Spring Boot Laboratory  
**Project:** Hostel Management System REST API

This project follows the four layers in the supplied mini-project specification:

1. Database layer – MySQL `HostelStudents` table
2. Persistence layer – JPA entity and `HostelRepository`
3. Business layer – `HostelService` interface and `HostelServiceImpl`
4. Presentation layer – REST endpoints under `/api/v1/hostel-students`

## Important note about the supplied specification

The specification lists `StudentID`, `StudentName`, `RoomNumber`, `AdmissionDate`, `FeesPaid`, and `PendingFees`, but also requires:

`PendingFees = TotalFees - FeesPaid`

Because `TotalFees` is needed for that calculation but is missing from the listed columns, this project adds a `TotalFees DECIMAL(10,2)` column and a matching DTO field. Mention this small schema clarification during your demonstration.

The room-number format was not defined in the PDF. This implementation accepts 1–10 letters, digits, or hyphens, for example `101`, `A101`, or `A-101`.

## Requirements

- JDK 17 or later
- MySQL Server 8.x
- Eclipse IDE for Enterprise Java and Web Developers (or another Java IDE)
- Maven (Eclipse can use its embedded Maven)
- Postman (recommended for testing)

## Step 1 – Create the database

1. Start MySQL Server.
2. Open MySQL Workbench or the MySQL command line.
3. Open and execute `database/hostel_management.sql`.
4. The script creates the `hostel_management` database and `HostelStudents` table.
5. It inserts two sample students. You can remove those INSERT statements if you want an empty table.

## Step 2 – Configure your MySQL password

Open:

`src/main/resources/application.properties`

Change:

`spring.datasource.password=YOUR_MYSQL_PASSWORD`

to your actual MySQL password. If your MySQL username is not `root`, change `spring.datasource.username` too.

Do not share your database password in GitHub. For a college local demo, keep your own password only in your local copy.

## Step 3 – Import into Eclipse

1. Extract `HostelManagementSystem.zip` to a folder.
2. Open Eclipse.
3. Select **File → Import**.
4. Select **Maven → Existing Maven Projects** and click **Next**.
5. Browse to the extracted `HostelManagementSystem` folder.
6. Ensure `pom.xml` is selected and click **Finish**.
7. Wait until Maven downloads the dependencies.
8. If errors remain, right-click the project → **Maven → Update Project** → select the project → **OK**.

## Step 4 – Run the application

1. In Package Explorer, open `src/main/java`.
2. Open `com.example.hostelmanagement`.
3. Right-click `HostelManagementApplication.java`.
4. Select **Run As → Java Application** (or **Spring Boot App**, if available).
5. Wait for the console message similar to `Started HostelManagementApplication`.
6. The server runs at `http://localhost:8080`.

There is no separate browser home page. This is a REST API; test the endpoints using Postman.

## Step 5 – Test using Postman

Base URL:

`http://localhost:8080/api/v1/hostel-students`

### A. Add a student

- Method: `POST`
- URL: `http://localhost:8080/api/v1/hostel-students`
- Body → raw → JSON

```json
{
  "studentName": "Akshata Metri",
  "roomNumber": "A-103",
  "admissionDate": "2026-08-01",
  "totalFees": 50000.00,
  "feesPaid": 30000.00
}
```

Expected: **201 Created**. The response includes `pendingFees: 20000.00`, calculated by the service.

### B. Display all students

- Method: `GET`
- URL: `http://localhost:8080/api/v1/hostel-students`

Expected: **200 OK** with a JSON array.

### C. Get a student by ID

- Method: `GET`
- URL: `http://localhost:8080/api/v1/hostel-students/1`

Replace `1` with an ID returned by your POST request.

### D. Update a student

- Method: `PUT`
- URL: `http://localhost:8080/api/v1/hostel-students/1`
- Body → raw → JSON

```json
{
  "studentName": "Akshata Metri",
  "roomNumber": "A-103",
  "admissionDate": "2026-08-01",
  "totalFees": 50000.00,
  "feesPaid": 40000.00
}
```

Expected: **200 OK**, with `pendingFees: 10000.00`.

### E. Delete a student

- Method: `DELETE`
- URL: `http://localhost:8080/api/v1/hostel-students/1`

Expected: **204 No Content**.

### F. Report – students with pending fees

- Method: `GET`
- URL: `http://localhost:8080/api/v1/hostel-students/reports/pending-fees`

Returns students whose pending fees are greater than zero.

### G. Report – students in a room

- Method: `GET`
- URL: `http://localhost:8080/api/v1/hostel-students/reports/room?roomNo=A-101`

Change `A-101` to the room number you want to search.

### H. Report – admissions between dates

- Method: `GET`
- URL: `http://localhost:8080/api/v1/hostel-students/reports/admissions?start=2026-08-01&end=2026-08-31`

Dates must use `YYYY-MM-DD`.

## Endpoint summary

| Operation | Method | Endpoint | Success status |
|---|---|---|---|
| Add student | POST | `/api/v1/hostel-students` | 201 |
| Display all | GET | `/api/v1/hostel-students` | 200 |
| Get by ID | GET | `/api/v1/hostel-students/{id}` | 200 |
| Update | PUT | `/api/v1/hostel-students/{id}` | 200 |
| Delete | DELETE | `/api/v1/hostel-students/{id}` | 204 |
| Pending fees report | GET | `/api/v1/hostel-students/reports/pending-fees` | 200 |
| Room report | GET | `/api/v1/hostel-students/reports/room?roomNo={roomNo}` | 200 |
| Admission date report | GET | `/api/v1/hostel-students/reports/admissions?start={date}&end={date}` | 200 |

## Validation and business rules

- Student name cannot be blank.
- Room number is required and must match the documented format in `HostelStudentDTO`.
- Total fees must be greater than zero.
- Fees paid cannot be negative or greater than total fees.
- Pending fees are calculated automatically; clients do not supply them.
- Admission date is required.
- The start date of an admission report cannot be after its end date.
- Missing student IDs return **404 Not Found**.
- Invalid input returns **400 Bad Request** with error details.

## Common problems

**1. Access denied / Access denied for user root**  
Check the MySQL username and password in `application.properties`.

**2. Unknown database `hostel_management`**  
Run `database/hostel_management.sql` in MySQL Workbench, or keep `createDatabaseIfNotExist=true` in the JDBC URL.

**3. Communications link failure / connection refused**  
Start the MySQL service and check that it is listening on port `3306`.

**4. Port 8080 already in use**  
Stop the other application using port 8080, or change `server.port=8081` in `application.properties`.

**5. Maven dependencies are not downloaded**  
Right-click project → Maven → Update Project. Check your internet connection.

**6. Table/column not found**  
Confirm the database script ran successfully and that the application connects to `hostel_management`.

## Suggested viva questions

1. What is Spring Boot?
2. What is a REST API?
3. What is the use of `@RestController`?
4. What is the difference between `@GetMapping`, `@PostMapping`, `@PutMapping`, and `@DeleteMapping`?
5. What is the purpose of `JpaRepository`?
6. What is the use of `@Entity` and `@Table`?
7. What is the purpose of the service layer?
8. What is DTO and why is it used?
9. How is pending fee calculated in this project?
10. What is the difference between HTTP status 200, 201, 204, 400, and 404?
11. What is Bean Validation?
12. Why is `BigDecimal` used for fee values?

## GitHub submission

1. Create a new GitHub repository, for example `Hostel-Management-System`.
2. Do not upload your real MySQL password. Replace it with a placeholder before pushing.
3. Upload the project source, SQL file, and README.
4. Copy the repository URL and submit it in the classroom assignment.
5. Test the project locally before your individual demonstration.
