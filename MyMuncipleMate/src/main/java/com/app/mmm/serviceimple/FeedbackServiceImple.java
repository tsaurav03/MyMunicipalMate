package com.app.mmm.serviceimple;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import com.app.mmm.dto.ApiResponse;
import com.app.mmm.dto.CitizenDto;
import com.app.mmm.dto.AddComplaintDTO;
import com.app.mmm.dto.FeedbackDTO;
import com.app.mmm.entity.Citizen;
import com.app.mmm.entity.Complaint;
import com.app.mmm.entity.Feedback;
import com.app.mmm.exception.ResourceNotFoundException;
import com.app.mmm.repository.CitizenRepository;
import com.app.mmm.repository.ComplaintRepository;
import com.app.mmm.repository.FeedbackRepository;
import com.app.mmm.service.FeedbackService;

@Service
@Transactional
public class FeedbackServiceImple implements FeedbackService {

	@Autowired
	private FeedbackRepository feedbackRepository;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private ComplaintRepository complaintRepository;

	@Autowired
	private CitizenRepository citizenRepository;

	@Override
	public ApiResponse addFeedback(String email, FeedbackDTO feedbackDTO)
			throws ResourceNotFoundException {
		Citizen citizen = citizenRepository.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("Citizen ID NOT FOUND"));

		Feedback feedback = new Feedback();

		feedback.setRating(feedbackDTO.getRating());
		feedback.setComment(feedbackDTO.getComment());
		feedback.setCitizen(citizen);

		citizen.addFeedback(feedback);

		feedbackRepository.save(feedback);

		return new ApiResponse("Feedback added successfully");
	}

	@Override
	public FeedbackDTO getFeedbackById(Long id) throws ResourceNotFoundException {
		Feedback feedback = feedbackRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(" FEEDBACK ID NOT FOUND"));
		FeedbackDTO feedbackDto = mapper.map(feedback, FeedbackDTO.class);

		System.out.println(feedbackDto.getComment());

		return feedbackDto;
	}

	@Override
	public ApiResponse removeFeedbackById(Long id) {
		String msg = "Deletion was Unsuccessful";
		Feedback feedbackToBeDeleted = feedbackRepository.findById(id)
				.orElseThrow(() -> new ResourceAccessException("ID NOT FOUND"));
		feedbackRepository.delete(feedbackToBeDeleted);
		return new ApiResponse("Feedback Deleted with Id: " + id);

		// was firing too many queries
//		if(feedbackRepository.existsById(id)) {
//			feedbackRepository.deleteById(id);
//			msg = "Feedback deleted successfully";
//		}

	}

	@Override
	public List<FeedbackDTO> getAllFeedbackOnAComplaint(Long complaintId) {
		Complaint complaint = complaintRepository.findById(complaintId)
				.orElseThrow(() -> new ResourceAccessException("Complaint Not found"));
		List<Feedback> feedbacks = complaint.getFeedbacks();
		
		if(complaint.getCitizen().getRoles().equals("ADMIN")) {
			return feedbacks.stream().map(feedback -> mapper.map(feedbacks, FeedbackDTO.class))
					.collect(Collectors.toList());
		}
		
		throw new ResourceAccessException("Only Admin Can Access all the Deedback");

		
	}

	@Override
	public List<FeedbackDTO> getAllFeedbackPostedBySingleCitizen(Long citizenId) {
		Citizen citizen = citizenRepository.findById(citizenId).orElseThrow(() -> new ResourceNotFoundException("Citizen Id Not Found"));
		
		return citizen.getFeedbacks().stream().map(feedback -> mapper.map(feedback, FeedbackDTO.class)).collect(Collectors.toList());
	}

	@Override
	public List<FeedbackDTO> getFeedbacks() {
		return feedbackRepository.findAll().stream().map(feedback -> mapper.map(feedback, FeedbackDTO.class)).collect(Collectors.toList());
	}
	
	

}
