# MyMunicipalMate

![MyMunicipalMate Logo](your-logo-url-here)

**MyMunicipalMate** is a robust municipal management system aimed at enhancing communication between citizens and municipal authorities. This project provides a platform for citizens to report various civic issues, submit feedback, and allows authorities to efficiently manage and address these complaints.

## Table of Contents

- [Features](#features)
- [Architecture](#architecture)
- [Installation](#installation)
- [Usage](#usage)
- [API Endpoints](#api-endpoints)
- [Technologies Used](#technologies-used)
- [Contributing](#contributing)
- [License](#license)

## Features

- **Citizen Management:** Users can sign up, log in, and manage their profiles.
- **Complaint Submission:** Citizens can report issues related to garbage management, water supply, electricity management, and road repair.
- **Feedback System:** Allows citizens to provide feedback to municipal authorities.
- **Team Management:** Admins can create teams, assign them to complaints, and manage their activities.
- **Google OAuth2 Login:** Users can log in using their Google accounts for quick access.
- **Role-based Access Control:** Secure endpoints with role-based authorization for both citizens and administrators.
- **JWT Authentication:** Secure API interactions with JWT tokens.
- **Multipart File Upload:** Support for uploading images related to complaints.

## Architecture

The project is structured as a Spring Boot application with a layered architecture:

- **Controllers:** Handle incoming HTTP requests and map them to the appropriate service methods.
- **Services:** Contain business logic and interact with repositories to fetch or persist data.
- **Repositories:** Interface with the database using JPA to perform CRUD operations.
- **Security:** JWT-based authentication and role-based authorization using Spring Security.

## Installation

### Prerequisites

- Java 17+
- Maven 3.6+
- MySQL Database
- Node.js (for frontend, if applicable)

### Steps

1. **Clone the repository:**
   ```bash
   git clone https://github.com/Himansh29/MyMuncipleMate.git
   cd MyMuncipleMate

## Table of Contents

- [Authentication](#authentication)
- [Admin Endpoints](#admin-endpoints)
- [Citizen Endpoints](#citizen-endpoints)
- [Complaint Endpoints](#complaint-endpoints)
- [Feedback Endpoints](#feedback-endpoints)
- [Team Endpoints](#team-endpoints)

## Authentication Endpoints

### Sign In
- **Endpoint:** `POST /api/auth/signin`
- **Request Body:** `SignInDTO`
- **Response:** `JwtAuthResponse`
- **Description:** Authenticates the user and returns a JWT token.

### Sign Up
- **Endpoint:** `POST /api/auth/signup`
- **Request Body:** `RegisterDTO`
- **Response:** `ApiResponse`
- **Description:** Registers a new user in the system.

### Forgot Password
- **Endpoint:** `POST /api/auth/forgotpassword`
- **Request Param:** `email`
- **Response:** `ApiResponse`
- **Description:** Sends an OTP to the user's email for password reset.

### Verify OTP and Reset Password
- **Endpoint:** `POST /api/auth/verify-otp`
- **Request Body:** `VerifyOtpDTO`
- **Response:** `ApiResponse`
- **Description:** Verifies the OTP and allows the user to reset the password.

### Admin Sign In
- **Endpoint:** `POST /api/auth/admin/signin`
- **Request Body:** `SignInDTO`
- **Response:** `JwtAuthResponse`
- **Description:** Authenticates an admin user and returns a JWT token.

## Admin Endpoints

### Add Role to Citizen
- **Endpoint:** `POST /api/admin/assign-role`
- **Request Body:** `RoleAssignmentDTO`
- **Response:** `ApiResponse`
- **Description:** Assigns a role to a citizen.

### Get All Complaints
- **Endpoint:** `GET /api/admin/complaints`
- **Response:** `List<ComplaintToBeShownOnAdminFeed>`
- **Description:** Retrieves all complaints.

### Get All Citizens
- **Endpoint:** `GET /api/admin/citizens`
- **Response:** `List<CitizenSummaryDto>`
- **Description:** Retrieves all citizens.

### Mark Complaint as Resolved
- **Endpoint:** `PATCH /api/admin/complaints/{complaintID}/resolved`
- **Path Variable:** `complaintID`
- **Response:** `ApiResponse`
- **Description:** Marks a complaint as resolved.

## Citizen Endpoints

### Get Citizen By ID
- **Endpoint:** `GET /api/citizens/{id}`
- **Path Variable:** `id`
- **Response:** `CitizenDto`
- **Description:** Retrieves a citizen by their ID.

### Get All Complaints Registered By A Single Citizen
- **Endpoint:** `GET /api/citizens/get_all_complaints/{citizenId}`
- **Path Variable:** `citizenId`
- **Response:** `List<ComplaintDTO>`
- **Description:** Retrieves all complaints registered by a specific citizen.

### Update Partial Citizen Details
- **Endpoint:** `PATCH /api/citizens/{citizenId}`
- **Path Variable:** `citizenId`
- **Request Body:** `Map<String, Object>`
- **Response:** `CitizenDto`
- **Description:** Updates partial details of a citizen.

## Complaint Endpoints

### Add Complaint By Citizen ID
- **Endpoint:** `POST /api/complaints/{citizenId}`
- **Path Variable:** `citizenId`
- **Request Body:** `AddComplaintDTO`
- **Response:** `ApiResponse`
- **Description:** Adds a new complaint for a citizen.

### Get Complaint By ID
- **Endpoint:** `GET /api/complaints/{id}`
- **Path Variable:** `id`
- **Response:** `ComplaintDTO`
- **Description:** Retrieves a complaint by its ID.

### Get Complaints By Status
- **Endpoint:** `GET /api/complaints/status/{status}`
- **Path Variable:** `status`
- **Response:** `List<ComplaintDTO>`
- **Description:** Retrieves complaints based on their status.

### Delete Complaint By ID
- **Endpoint:** `DELETE /api/complaints/{complaintId}`
- **Path Variable:** `complaintId`
- **Response:** `ApiResponse`
- **Description:** Deletes a complaint by its ID.

### Get All Complaints
- **Endpoint:** `GET /api/complaints/`
- **Response:** `List<ComplainToBeSHownOnFeedDTO>`
- **Description:** Retrieves all complaints.

### Add Garbage Management Complaint
- **Endpoint:** `POST /api/complaints/garbage-management/{citizenId}`
- **Path Variable:** `citizenId`
- **Request Body:** `AddComplaintDTO`
- **Response:** `ApiResponse`
- **Roles:** `ADMIN`, `CITIZEN`
- **Description:** Adds a new garbage management complaint.

### Add Water Supply Complaint
- **Endpoint:** `POST /api/complaints/water-supply/{citizenId}`
- **Path Variable:** `citizenId`
- **Request Body:** `AddComplaintDTO`
- **Response:** `ApiResponse`
- **Roles:** `ADMIN`, `CITIZEN`
- **Description:** Adds a new water supply complaint.

### Add Electricity Management Complaint
- **Endpoint:** `POST /api/complaints/electricity-management/{citizenId}`
- **Path Variable:** `citizenId`
- **Request Body:** `AddComplaintDTO`
- **Response:** `ApiResponse`
- **Roles:** `ADMIN`, `CITIZEN`
- **Description:** Adds a new electricity management complaint.

### Add Road Repair Complaint
- **Endpoint:** `POST /api/complaints/road-repair/{citizenId}`
- **Path Variable:** `citizenId`
- **Request Body:** `AddComplaintDTO`
- **Response:** `ApiResponse`
- **Roles:** `ADMIN`, `CITIZEN`
- **Description:** Adds a new road repair complaint.

## Feedback Endpoints

### Add Feedback
- **Endpoint:** `POST /api/feedback/{complaintId}/{citizenId}`
- **Path Variables:** `complaintId`, `citizenId`
- **Request Body:** `FeedbackDTO`
- **Response:** `FeedbackDTO`
- **Description:** Adds feedback for a specific complaint.

### Get Feedback By ID
- **Endpoint:** `GET /api/feedback/{id}`
- **Path Variable:** `id`
- **Response:** `FeedbackDTO`
- **Description:** Retrieves feedback by its ID.

### Delete Feedback By ID
- **Endpoint:** `DELETE /api/feedback/{feedbackId}`
- **Path Variable:** `feedbackId`
- **Response:** `ApiResponse`
- **Description:** Deletes feedback by its ID.

## Team Endpoints

### Create Team
- **Endpoint:** `POST /api/teams`
- **Request Body:** `TeamDTO`
- **Response:** `TeamDTO`
- **Description:** Creates a new team.

### Get Team By ID
- **Endpoint:** `GET /api/teams/{id}`
- **Path Variable:** `id`
- **Response:** `TeamDTO`
- **Description:** Retrieves a team by its ID.

### Get All Teams
- **Endpoint:** `GET /api/teams`
- **Response:** `List<TeamDTO>`
- **Description:** Retrieves all teams.

### Update Team
- **Endpoint:** `PUT /api/teams/{id}`
- **Path Variable:** `id`
- **Request Body:** `TeamDTO`
- **Response:** `TeamDTO`
- **Description:** Updates a team by its ID.

### Delete Team
- **Endpoint:** `DELETE /api/teams/{id}`
- **Path Variable:** `id`
- **Response:** `ApiResponse`
- **Description:** Deletes a team by its ID.

