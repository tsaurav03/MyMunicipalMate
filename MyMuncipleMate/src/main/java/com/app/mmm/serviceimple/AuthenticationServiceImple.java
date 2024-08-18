package com.app.mmm.serviceimple;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.app.mmm.dto.ApiResponse;
import com.app.mmm.dto.RegisterDTO;
import com.app.mmm.dto.SignInDTO;
import com.app.mmm.dto.VerifyOtpDTO;
import com.app.mmm.entity.Citizen;
import com.app.mmm.entity.OTPEntity;
import com.app.mmm.entity.Role;
import com.app.mmm.exception.ResourceNotFoundException;
import com.app.mmm.repository.CitizenRepository;
import com.app.mmm.repository.OtpRepository;
import com.app.mmm.repository.RoleRepository;
import com.app.mmm.security.JwtAuthResponse;
import com.app.mmm.security.JwtTokenProvider;
import com.app.mmm.service.AuthenticationService;

@Service
@Transactional
public class AuthenticationServiceImple implements AuthenticationService {

	@Autowired
	private CitizenRepository citizenRepository;

	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private OtpRepository otpRepository;

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
    private JavaMailSender mailSender;
	
	

	@Override
	public String signIn(SignInDTO signInDTO) {

		Authentication authenticate = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(signInDTO.getUsernameOrEmail(), signInDTO.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authenticate);

		String token = jwtTokenProvider.generateToken(authenticate);

		return token;
	}

	@Override
	public ApiResponse register(RegisterDTO regisetrDTO) {

		if (citizenRepository.existsByUsername(regisetrDTO.getUsername())) {
			throw new ResourceNotFoundException(HttpStatus.BAD_REQUEST, "Username already exists");
		}

		if (citizenRepository.existsByEmail(regisetrDTO.getEmail())) {
			throw new ResourceNotFoundException(HttpStatus.BAD_REQUEST, "Email already exists");
		}

		Citizen citizen = new Citizen();
		citizen.setFirstName(regisetrDTO.getFirstName());
		citizen.setLastName(regisetrDTO.getLastName());
		citizen.setUsername(regisetrDTO.getUsername());
		citizen.setEmail(regisetrDTO.getEmail());
		citizen.setPassword(encoder.encode(regisetrDTO.getPassword()));

		Set<Role> roles = new HashSet<>();
		Role citizenRole = roleRepository.findByName("ROLE_CITIZEN")
				.orElseThrow(() -> new ResourceNotFoundException("ROLE NOT FOUND"));
		roles.add(citizenRole);

		citizen.setRoles(roles);

		citizenRepository.save(citizen);
		return new ApiResponse("Registration successful");
	}

	private Map<String, String> otpStorage = new ConcurrentHashMap<>();

	@Override
    public ApiResponse sendOtpForPasswordReset(String email) {
        Citizen citizen = citizenRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Email not found"));

        // Generate 4-digit OTP
        int otp = (int)(Math.random() * 9000) + 1000;

        // Save OTP in the database linked to the user
        OTPEntity otpEntity = new OTPEntity(citizen.getEmail(), otp);
        otpRepository.save(otpEntity);

        // Send OTP via email
        sendOtpEmail(citizen.getEmail(), otp);

        return new ApiResponse("OTP sent to email successfully");
    }

    @Override
    public ApiResponse verifyOtpAndResetPassword(VerifyOtpDTO verifyOtpDTO) {
    	OTPEntity otpEntity = otpRepository.findByEmailAndOtp(verifyOtpDTO.getEmail(), verifyOtpDTO.getOtp())
                .orElseThrow(() -> new IllegalArgumentException("Invalid OTP"));

        if (!verifyOtpDTO.getNewPassword().equals(verifyOtpDTO.getConfirmPassword())) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        Citizen citizen = citizenRepository.findByEmail(verifyOtpDTO.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Email not found"));

        // Update password
        citizen.setPassword(encoder.encode(verifyOtpDTO.getNewPassword()));
        citizenRepository.save(citizen);

        // Remove OTP after successful verification
        otpRepository.delete(otpEntity);

        return new ApiResponse("Password reset successful");
    }

    private void sendOtpEmail(String toEmail, int otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Password Reset OTP");
        message.setText("Your OTP for password reset is: " + otp);
        mailSender.send(message);
    }

	@Override
	public String adminSignIn(SignInDTO signInDTO) {
		Authentication authenticate = authenticationManager.authenticate(
	            new UsernamePasswordAuthenticationToken(signInDTO.getUsernameOrEmail(), signInDTO.getPassword()));

		boolean isAdmin = authenticate.getAuthorities().stream()
	            .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));

	    if (!isAdmin) {
	        throw new IllegalArgumentException("You must be an admin to log in here");
	    }

	    String token = jwtTokenProvider.generateToken(authenticate);

	    return token;
	}

	@Override
	public String createTokenForOAuth2User(OAuth2User oAuth2User) {
		Authentication authentication = new UsernamePasswordAuthenticationToken(
		        oAuth2User, null, oAuth2User.getAuthorities()
		    );
		    return jwtTokenProvider.generateToken(authentication);
	}
	
	
	

}
