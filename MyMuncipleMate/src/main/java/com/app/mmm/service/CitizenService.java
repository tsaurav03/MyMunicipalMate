package com.app.mmm.service;

import java.util.List;
import java.util.Map;

import com.app.mmm.dto.AddComplaintDTO;
import com.app.mmm.dto.ApiResponse;
import com.app.mmm.dto.CitizenDto;
import com.app.mmm.dto.CitizenDtoWithComplaints;
import com.app.mmm.dto.CitizenSummaryDto;
import com.app.mmm.dto.RoleAssignmentDTO;

public interface CitizenService {
	CitizenDtoWithComplaints getCitizen(Long id);
	ApiResponse deleteCitizenByID(Long id);
	List<AddComplaintDTO> getAllComplaintsByCitizen(Long id);
	CitizenDto updateCitizenById(Long id, CitizenDto citizenDto);
	// un-added 
	List<CitizenSummaryDto> getAllCitizen();
	ApiResponse addRoleToCitizen(RoleAssignmentDTO assignmentDTO);
	CitizenDto updatePartialCitizenDetails(Long citizenId, Map<String, Object> updates);
}
