package com.app.mmm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.mmm.dto.ApiResponse;
import com.app.mmm.dto.CitizenSummaryDto;
import com.app.mmm.dto.ComplaintToBeShownOnAdminFeed;
import com.app.mmm.dto.RoleAssignmentDTO;
import com.app.mmm.service.CitizenService;
import com.app.mmm.service.ComplaintService;
import com.app.mmm.service.TeamService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {

    @Autowired
    private CitizenService citizenService;

    @Autowired
    private ComplaintService complaintService;
    
    @Autowired
    private TeamService teamService;

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(description = "Add Role to Citizen")
    @PostMapping("/assign-role")
    public ResponseEntity<?> addRoleToCitizen(@Validated @RequestBody RoleAssignmentDTO roleAssignmentDTO) {
        ApiResponse response = citizenService.addRoleToCitizen(roleAssignmentDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(description = "Get All Complaints")
    @GetMapping("/complaints")
    public ResponseEntity<List<ComplaintToBeShownOnAdminFeed>> getAllComplaints() {
        List<ComplaintToBeShownOnAdminFeed> complaints = complaintService.getAllComplaintsForAdmin();
        return ResponseEntity.status(HttpStatus.OK).body(complaints);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(description = "Get All Citizens")
    @GetMapping("/citizens")
    public ResponseEntity<List<CitizenSummaryDto>> getAllCitizens() {
        List<CitizenSummaryDto> citizens = citizenService.getAllCitizen();
        return ResponseEntity.status(HttpStatus.OK).body(citizens);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(description = "Mark Complaint as Resolved")
    @PatchMapping("/complaints/{complaintID}/resolved")
    public ResponseEntity<?> markComplaintAsResolved(@PathVariable Long complaintID) {
        try {
            complaintService.markComplaintAsResolved(complaintID);
            return ResponseEntity.status(HttpStatus.OK).body("Complaint marked as RESOLVED");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(description = "Mark Complaint as Open")
    @PatchMapping("/complaints/{complaintID}/open")
    public ResponseEntity<?> markComplaintAsOpen(@PathVariable Long complaintID) {
        try {
            complaintService.markComplaintAsOpen(complaintID);
            return ResponseEntity.status(HttpStatus.OK).body("Complaint marked as OPEN");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(description = "Assign Team to the Complaint")
    @PutMapping("/assign-team/{complaintId}")
    public ResponseEntity<?> assignTeamToComplaint(@PathVariable Long complaintId) {
        try {
        	teamService.assignTeamToComplaint(complaintId);
            return ResponseEntity.status(HttpStatus.OK).body("Team assigned to complaint successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}


