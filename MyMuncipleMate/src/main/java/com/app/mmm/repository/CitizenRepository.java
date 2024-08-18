package com.app.mmm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.mmm.entity.Citizen;

public interface CitizenRepository extends JpaRepository<Citizen, Long> {
	Optional<Citizen> findByEmail(String email);
	boolean existsByEmail(String email);
	boolean existsByUsername(String username);
	Optional<Citizen> findByUsernameOrEmail(String username, String email);
}
