package com.app.mmm;

import java.time.LocalDateTime;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderImpl {

	public static void main(String[] args) {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		
		System.out.println(encoder.encode("0101001"));
		System.out.println(encoder.encode("ADMIN"));
		
		System.out.println(LocalDateTime.now());
	}
}
