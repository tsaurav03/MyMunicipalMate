package com.app.mmm.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyOtpDTO {
	private String email;
    private int otp;
    private String newPassword;
    private String confirmPassword;
}
