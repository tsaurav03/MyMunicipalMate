package com.app.mmm.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.mmm.dto.ApiResponse;
import com.app.mmm.dto.FeedbackDTO;
import com.app.mmm.entity.Feedback;
import com.app.mmm.service.FeedbackService;

@RestController
@RequestMapping("/api/feedback")
@CrossOrigin(origins = "http://localhost:3000")
public class FeedbackController {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private FeedbackService feedbackService;
    
    @PreAuthorize("hasRole('ADMIN') or hasRole('CITIZEN')")
    @GetMapping("/")
    public ResponseEntity<?> getFeedbacks() {
    	return ResponseEntity.ok(feedbackService.getFeedbacks());
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('CITIZEN')")
    @PostMapping("/{email}")
    public ResponseEntity<?> addFeedback(@PathVariable String email,
                                         @RequestBody FeedbackDTO feedbackdto) {
        try {
            Feedback feedback = new Feedback();
            feedback.setRating(feedbackdto.getRating());
            feedback.setComment(feedbackdto.getComment());

            FeedbackDTO dto = mapper.map(feedback, FeedbackDTO.class);

            return ResponseEntity.status(HttpStatus.CREATED).body(feedbackService.addFeedback(email, dto));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('CITIZEN')")
    @GetMapping("/{feedbackId}")
    public ResponseEntity<?> getFeedbackById(@PathVariable Long feedbackId) {
        try {
            return ResponseEntity.ok(feedbackService.getFeedbackById(feedbackId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage()));
        }
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('CITIZEN')")
    @DeleteMapping("/{feedbackId}")
    public ResponseEntity<?> deleteFeedback(@PathVariable Long feedbackId) {
        try {
            return ResponseEntity.ok(feedbackService.removeFeedbackById(feedbackId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage()));
        }
    }
}

