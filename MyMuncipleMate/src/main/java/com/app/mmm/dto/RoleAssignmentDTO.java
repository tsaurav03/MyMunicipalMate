package com.app.mmm.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleAssignmentDTO {
	@NotBlank(message = "Email is mandatory")
    private String email;
	@NotBlank(message = "Role name is mandatory")
    private String roleName;
}