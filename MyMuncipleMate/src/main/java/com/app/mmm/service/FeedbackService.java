package com.app.mmm.service;

import java.util.List;

import com.app.mmm.dto.ApiResponse;
import com.app.mmm.dto.FeedbackDTO;
import com.app.mmm.entity.Complaint;
import com.app.mmm.entity.Feedback;

public interface FeedbackService {

	ApiResponse addFeedback(String citizenEmail, FeedbackDTO dto);
	FeedbackDTO getFeedbackById(Long id);
	ApiResponse removeFeedbackById(Long id);
	List<FeedbackDTO> getAllFeedbackOnAComplaint(Long complaintId);
	List<FeedbackDTO> getAllFeedbackPostedBySingleCitizen(Long citizenId);
	List<FeedbackDTO> getFeedbacks();
}
