package com.app.mmm.dto;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SignInDTO {

	@NotEmpty(message = "Username or email must not be empty")
    private String usernameOrEmail;
    
    @NotEmpty(message = "Password must not be empty")
    private String password;
}
