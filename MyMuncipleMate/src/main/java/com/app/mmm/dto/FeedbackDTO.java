package com.app.mmm.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackDTO {
	
	@NotNull(message = "Rating must not be null")
    private int rating;
    @NotEmpty(message = "Feedback content must not be empty")
    private String comment;
}
