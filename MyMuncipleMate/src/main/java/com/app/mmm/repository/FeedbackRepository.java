package com.app.mmm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.mmm.entity.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

}
