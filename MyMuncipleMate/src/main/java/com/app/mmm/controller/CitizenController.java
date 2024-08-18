package com.app.mmm.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.mmm.dto.CitizenDto;
import com.app.mmm.service.CitizenService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/citizens")
@CrossOrigin(origins = "http://localhost:3000")
public class CitizenController {

	@Autowired
	private CitizenService citizenService;

	@PreAuthorize("hasRole('ADMIN') or hasRole('CITIZEN')")
	@Operation(description = "Get Citizen By id")
	@GetMapping("/{id}")
	public ResponseEntity<?> getCitizen(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(citizenService.getCitizen(id));
	}

	@PreAuthorize("hasRole('ADMIN') or hasRole('CITIZEN')")
	@Operation(description = "Get All Complaints Registered By A Single Citizen")
	@GetMapping("/get_all_complaints/{citizenId}")
	public ResponseEntity<?> getAllComplaintsByCitizen(@PathVariable Long citizenId) {
		return ResponseEntity.status(HttpStatus.OK).body(citizenService.getAllComplaintsByCitizen(citizenId));
	}

	@PreAuthorize("hasRole('ADMIN') or hasRole('CITIZEN')")
	@PatchMapping("/{citizenId}")
	@Operation(description = "Update Partial Citizen Details")
	public ResponseEntity<?> updatePartialCitizenDetails(@PathVariable Long citizenId,
			@RequestBody Map<String, Object> updates) {
		CitizenDto updatedCitizen = citizenService.updatePartialCitizenDetails(citizenId, updates);
		return ResponseEntity.ok(updatedCitizen);
	}

}
