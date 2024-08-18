package com.app.mmm.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDTO {

	@NotEmpty(message = "First name must not be empty")
    private String firstName;

    @NotEmpty(message = "Last name must not be empty")
    private String lastName;

    @NotEmpty(message = "Username must not be empty")
    private String username;

    @Email(message = "Email should be valid")
    @NotEmpty(message = "Email must not be empty")
    private String email;

    @NotEmpty(message = "Password must not be empty")
    private String password;

    @NotEmpty(message = "Confirm password must not be empty")
    private String confirmPassword;
}
