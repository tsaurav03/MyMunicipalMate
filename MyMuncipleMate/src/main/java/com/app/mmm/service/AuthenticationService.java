package com.app.mmm.service;

import org.springframework.security.oauth2.core.user.OAuth2User;

import com.app.mmm.dto.ApiResponse;
import com.app.mmm.dto.ForgotPasswordDTO;
import com.app.mmm.dto.RegisterDTO;
import com.app.mmm.dto.SignInDTO;
import com.app.mmm.dto.VerifyOtpDTO;

public interface AuthenticationService {
	String signIn(SignInDTO signInDTO);
	ApiResponse register(RegisterDTO dto);
	ApiResponse sendOtpForPasswordReset(String email);
    ApiResponse verifyOtpAndResetPassword(VerifyOtpDTO verifyOtpDTO);
    String adminSignIn(SignInDTO signInDTO);
	String createTokenForOAuth2User(OAuth2User oAuth2User);
}
