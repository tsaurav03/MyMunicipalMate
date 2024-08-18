package com.app.mmm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.mmm.entity.OTPEntity;

public interface OtpRepository extends JpaRepository<OTPEntity, Long> {

	Optional<OTPEntity> findByEmailAndOtp(String email, int otp);
}
