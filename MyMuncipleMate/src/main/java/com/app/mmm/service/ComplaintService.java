package com.app.mmm.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.app.mmm.dto.AddComplaintDTO;
import com.app.mmm.dto.ApiResponse;
import com.app.mmm.dto.ComplainToBeSHownOnFeedDTO;
import com.app.mmm.dto.ComplaintDTO;
import com.app.mmm.dto.ComplaintToBeShownOnAdminFeed;
import com.app.mmm.enums.ComplaintType;
import com.app.mmm.exception.ResourceNotFoundException;

public interface ComplaintService {

	ApiResponse addComplaint(MultipartFile file, AddComplaintDTO complaintDTO, String email,
			ComplaintType complaintType) throws ResourceNotFoundException;
    ComplaintDTO getComplaintById(Long id); 
    ApiResponse deleteComplaintById(Long id);
    List<ComplainToBeSHownOnFeedDTO> getAllComplaints();
    List<ComplaintToBeShownOnAdminFeed> getAllComplaintsForAdmin();
    List<ComplaintDTO> getComplaintsByStatus(String status);
	ApiResponse changeStatus(Long id, String status); 
	ApiResponse markComplaintAsResolved(Long id);
    ApiResponse markComplaintAsOpen(Long id);
}
