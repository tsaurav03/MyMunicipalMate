package com.app.mmm.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.mmm.dto.AddComplaintDTO;
import com.app.mmm.dto.ApiResponse;
import com.app.mmm.dto.ComplainToBeSHownOnFeedDTO;
import com.app.mmm.dto.ComplaintDTO;
import com.app.mmm.enums.ComplaintType;
import com.app.mmm.service.ComplaintService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/complaints")
@CrossOrigin(origins = "http://localhost:3000")
public class ComplaintController {

	@Autowired
	private ComplaintService complaintService;

	@PreAuthorize("hasRole('ADMIN') or hasRole('CITIZEN')")
    @Operation(description = "Add Garbage Management Complaint")
    @PostMapping("/garbage-management/{email}")
    public ResponseEntity<ApiResponse> addGarbageManagementComplaint(
            @PathVariable String email,
            @RequestParam("file") MultipartFile file,
            @RequestPart("complaint") AddComplaintDTO complaintDTO) {
        try {
            ApiResponse response = complaintService.addComplaint(file, complaintDTO, email, ComplaintType.GARBAGE_MANAGEMENT);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(e.getMessage()));
        }
    }
	
	@PreAuthorize("hasRole('ADMIN') or hasRole('CITIZEN')")
    @Operation(description = "Add Water Supply Complaint")
    @PostMapping("/water-supply/{email}")
    public ResponseEntity<ApiResponse> addWaterSupplyComplaint(
            @PathVariable String email,
            @RequestParam("file") MultipartFile file,
            @RequestPart("complaint") AddComplaintDTO complaintDTO) {
        try {
            ApiResponse response = complaintService.addComplaint(file, complaintDTO, email, ComplaintType.WATER_SUPPLY);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(e.getMessage()));
        }
    }
	
	@PreAuthorize("hasRole('ADMIN') or hasRole('CITIZEN')")
    @Operation(description = "Add Road Repair Complaint")
    @PostMapping("/road-repair/{email}")
    public ResponseEntity<ApiResponse> addRoadRepairComplaint(
            @PathVariable String email,
            @RequestParam("file") MultipartFile file,
            @RequestPart("complaint") AddComplaintDTO complaintDTO) {
        try {
            ApiResponse response = complaintService.addComplaint(file, complaintDTO, email, ComplaintType.ROAD_REPAIR);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(e.getMessage()));
        }
    }
	
	@PreAuthorize("hasRole('ADMIN') or hasRole('CITIZEN')")
    @Operation(description = "Add Sanitation Issues Complaint")
    @PostMapping("/sanitation-issues/{email}")
    public ResponseEntity<ApiResponse> addSanitationIssuesComplaint(
            @PathVariable String email,
            @RequestParam("file") MultipartFile file,
            @RequestPart("complaint") AddComplaintDTO complaintDTO) {
        try {
            ApiResponse response = complaintService.addComplaint(file, complaintDTO, email, ComplaintType.SANITATION_ISSUES);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(e.getMessage()));
        }
    }
	
	@PreAuthorize("hasRole('ADMIN') or hasRole('CITIZEN')")
    @Operation(description = "Add Traffic Management Complaint")
    @PostMapping("/traffic-management/{email}")
    public ResponseEntity<ApiResponse> addTrafficManagementComplaint(
            @PathVariable String email,
            @RequestParam("file") MultipartFile file,
            @RequestPart("complaint") AddComplaintDTO complaintDTO) {
        try {
            ApiResponse response = complaintService.addComplaint(file, complaintDTO, email, ComplaintType.TRAFFIC_MANAGEMENT);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(e.getMessage()));
        }
    }
	
	@PreAuthorize("hasRole('ADMIN') or hasRole('CITIZEN')")
    @Operation(description = "Add Environmental Hazards Complaint")
    @PostMapping("/environmental-hazards/{email}")
    public ResponseEntity<ApiResponse> addEnvironmentalHazardsComplaint(
            @PathVariable String email,
            @RequestParam("file") MultipartFile file,
            @RequestPart("complaint") AddComplaintDTO complaintDTO) {
        try {
            ApiResponse response = complaintService.addComplaint(file, complaintDTO, email, ComplaintType.ENVIRONMENTAL_HAZARDS);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(e.getMessage()));
        }
    }
	
	@PreAuthorize("hasRole('ADMIN') or hasRole('CITIZEN')")
    @Operation(description = "Add Fire Safety Complaint")
    @PostMapping("/fire-safety/{email}")
    public ResponseEntity<ApiResponse> addFireSafetyComplaint(
            @PathVariable String email,
            @RequestParam("file") MultipartFile file,
            @RequestPart("complaint") AddComplaintDTO complaintDTO) {
        try {
            ApiResponse response = complaintService.addComplaint(file, complaintDTO, email, ComplaintType.FIRE_SAFETY);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(e.getMessage()));
        }
    }
	
	@PreAuthorize("hasRole('ADMIN') or hasRole('CITIZEN')")
    @Operation(description = "Add Electricity Management Complaint")
    @PostMapping("/electricity-management/{email}")
    public ResponseEntity<ApiResponse> addElectricityManagementComplaint(
            @PathVariable String email,
            @RequestParam("file") MultipartFile file,
            @RequestPart("complaint") AddComplaintDTO complaintDTO) {
        try {
            ApiResponse response = complaintService.addComplaint(file, complaintDTO, email, ComplaintType.ELECTRICITY_MANAGEMENT);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(e.getMessage()));
        }
    }

	@PreAuthorize("hasRole('ADMIN') or hasRole('CITIZEN')")
	@Operation(description = "Get Complaint By ID")
	@GetMapping("/{id}")
	public ResponseEntity<ComplaintDTO> getComplaint(@PathVariable Long id) {
		try {
			ComplaintDTO complaintDTO = complaintService.getComplaintById(id);
			return ResponseEntity.ok(complaintDTO);
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@PreAuthorize("hasRole('ADMIN') or hasRole('CITIZEN')")
	@Operation(description = "Get Complaints By Status")
	@GetMapping("/status/{status}")
	public ResponseEntity<List<ComplaintDTO>> getComplaintsByStatus(@PathVariable String status) {
		List<ComplaintDTO> complaints = complaintService.getComplaintsByStatus(status);
		return ResponseEntity.ok(complaints);
	}

	@PreAuthorize("hasRole('ADMIN') or hasRole('CITIZEN')")
	@Operation(description = "Delete Complaint By ID")
	@DeleteMapping("/{complaintId}")
	public ResponseEntity<ApiResponse> deleteComplaint(@PathVariable Long complaintId) {
		try {
			ApiResponse response = complaintService.deleteComplaintById(complaintId);
			return ResponseEntity.ok(response);
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage()));
		}
	}

	@Operation(description = "Get All Complaints")
	@GetMapping("/")
	public ResponseEntity<List<ComplainToBeSHownOnFeedDTO>> getAllComplaints() {
		List<ComplainToBeSHownOnFeedDTO> complaints = complaintService.getAllComplaints();
		return ResponseEntity.ok(complaints);
	}
	
	@GetMapping()
    public void redirectToGoogleLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String redirectUrl = "/oauth2/authorization/google";
        response.sendRedirect(redirectUrl);
    }
}
