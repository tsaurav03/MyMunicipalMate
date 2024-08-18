package com.app.mmm.dto;

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
public class ComplainToBeSHownOnFeedDTO {

	private byte[] imageData;
	private String location;
	private Status status;
	private String username;
	private String complaintDescription;
	private ComplaintType complaintType;
}
