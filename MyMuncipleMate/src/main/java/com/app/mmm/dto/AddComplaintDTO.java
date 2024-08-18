package com.app.mmm.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
@AllArgsConstructor
public class AddComplaintDTO {

    @NotEmpty(message = "Description must not be empty")
    private String complaintDescription;

    @NotEmpty(message = "Location must not be empty")
    @NotBlank
    private String location;
    
    private MultipartFile image;
}
