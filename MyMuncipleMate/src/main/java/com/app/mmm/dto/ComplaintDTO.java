package com.app.mmm.dto;

import java.util.List;

import com.app.mmm.enums.ComplaintType;
import com.app.mmm.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class ComplaintDTO {

	private ComplaintType complaintType;
	private String complaintDescription;
	private Status status;
	private String location;
	private int likes;
	private String imageData;
	private CitizenDto citizen;
	private List<FeedbackDTO> feedbacks;
	private TeamDTO team;
}
