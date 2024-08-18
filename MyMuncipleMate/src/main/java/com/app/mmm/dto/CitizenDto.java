package com.app.mmm.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
public class CitizenDto {

	@NotEmpty(message = "First name must not be empty")
	@Size(min = 2, message = "First name must be at least 2 characters")
	private String firstName;

	@NotEmpty(message = "Last name must not be empty")
	@Size(min = 2, message = "Last name must be at least 2 characters")
	private String lastName;

	@NotEmpty(message = "Username must not be empty")
	@Size(min = 3, message = "Username must be at least 3 characters")
	private String username;

	@NotEmpty(message = "Email must not be empty")
	@Email(message = "Email should be valid")
	private String email;

	@JsonProperty(access = Access.WRITE_ONLY)
	@NotEmpty(message = "Password must not be empty")
	@Size(min = 6, message = "Password must be at least 6 characters")
	private String password;

	@JsonProperty(access = Access.WRITE_ONLY)
	@NotEmpty(message = "Confirm password must not be empty")
	@Size(min = 6, message = "Confirm password must be at least 6 characters")
	private String confirmPassword;

}
