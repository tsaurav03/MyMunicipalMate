package com.app.mmm.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ForgotPasswordDTO {

    @NotEmpty(message = "Email must not be empty")
    @Email(message = "Email should be valid")
    private String email;
    
    @NotEmpty(message = "OTP must not be empty")
    private String otp;
    
    @NotEmpty(message = "Password must not be empty")
    private String password;
    
    @NotEmpty(message = "Confirm password must not be empty")
    private String confirmPassword;

}
