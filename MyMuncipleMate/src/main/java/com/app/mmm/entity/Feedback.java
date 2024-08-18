package com.app.mmm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.springframework.lang.Nullable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Feedback extends BaseEntity {

	@ManyToOne
    @JoinColumn(name = "complaint_id", nullable = false)
	@Nullable
    private Complaint complaint;

    @ManyToOne
    @JoinColumn(name = "citizen_id", nullable = false)
    private Citizen citizen;

    @Column(length = 10, nullable = true)
    private int rating;

    @Column(length = 500, nullable = true)
    private String comment;
}
