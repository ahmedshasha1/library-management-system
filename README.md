# library-management-system

-- Overview
This project is a Library Management System built with Spring Boot 3, Spring Security 6, and JPA/Hibernate.  
It allows managing books, authors, publishers, categories, members, staff and borrowing transactions with role-based access control.

-- Design

 1. Database Design (ERD)
- Users.
- Member ↔ BorrowTransaction → One-to-Many.  
- Book ↔ Author → Many-to-Many.  
- Book ↔ Category → Many-to-Many.
- Book ↔ Publisher → Many-to-One.
- Book ↔ BorrowTransaction → one-to-Many.  
- Transaction includes borrow and return dates, plus status (`BORROWED`, `RETURNED`).  
- Staff / Librarian / Admin are modeled as Users with different roles.

 2. Architecture

- Layered Architecture (Controller → Service → Repository)**  
  - Controller : Exposes REST APIs.  
  - Service : Contains business logic.  
  - Repository : Handles database interaction.  
   -   Mapper : Responsible for converting Entities to DTOs and vice versa.
                      It uses MapStruct or manual mappers.
                      Purpose: To separate the domain logic from the data format exposed to the client.

- Separation of concerns makes the system clean, testable and easier to maintain.

 3. Security
- Implemented using Spring Security 6.  
- Role-based access control with @PreAuthorize.  

| Role       | Permissions                                                                     |
|------------|---------------------------------------------------------------------------------|
| ADMIN      | Full access (manage books, categories, members, users, publisher, transactions) |
| LIBRARIAN  | Manage borrowing/return transactions, assist members                            |
| STAFF      | Support queries (view members, transactions, reports)                           |


4. Pagination & Search
- APIs use pagination (pageNo, pageSize) for efficiency.  
- Search endpoints available for name, email, category, author, publisher.

5. REST API Conventions
- Endpoints follow REST best practices:  
  - /books/{id}, /members/{id}, /transactions/{id}.  
- Meaningful HTTP response codes:  
  - 201 Created → new resources,  
  - 200 OK → successful retrieval,  
  - 204 No Content → deletions.
