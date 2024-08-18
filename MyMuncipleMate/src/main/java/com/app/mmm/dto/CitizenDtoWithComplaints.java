package com.app.mmm.dto;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CitizenDtoWithComplaints {

	@NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String username;

    @Email
    private String email;

    @JsonProperty(access = Access.WRITE_ONLY)
    private String password;

    private List<AddComplaintDTO> complaints;
    private List<FeedbackDTO> feedbacks;
}
