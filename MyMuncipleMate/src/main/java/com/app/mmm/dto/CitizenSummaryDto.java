package com.app.mmm.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CitizenSummaryDto {
    private String name; // Combination of firstName and lastName
    private List<ComplaintDTO> complaints;
    private List<FeedbackDTO> feedbacks;

}
