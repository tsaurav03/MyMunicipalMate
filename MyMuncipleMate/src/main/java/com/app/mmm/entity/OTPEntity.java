package com.app.mmm.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import lombok.RequiredArgsConstructor;

@Entity
@RequiredArgsConstructor
public class OTPEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	private String email;
    private int otp;
    @CreationTimestamp
    private LocalDateTime createdAt;
    
	public OTPEntity(String email, int otp) {
		super();
		this.email = email;
		this.otp = otp;
	}
    
    
}
