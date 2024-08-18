package com.app.mmm.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.lang.Nullable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "Citizens", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username"}),
        @UniqueConstraint(columnNames = {"email"})
})
@Getter
@Setter
public class Citizen extends BaseEntity {

	@Column(length = 50, nullable = false)
    private String firstName;

    @Column(length = 50, nullable = false)
    private String lastName;

    @Column(length = 50, nullable = false, unique = true)
    private String username;

    @Column(length = 40, nullable = false)
    private String email;

    @Column(length = 60, nullable = false)
    private String password;

    @OneToMany(mappedBy = "citizen", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Complaint> complaints = new ArrayList<>();

    @OneToMany(mappedBy = "citizen", cascade = CascadeType.ALL, orphanRemoval = true)
    @Nullable
    private List<Feedback> feedbacks = new ArrayList<>();
    
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "citizen_roles",
               joinColumns = @JoinColumn(name = "citizen_id"),
               inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
    
    public void addRole(Role role) {
        this.roles.add(role);
    }

    public void removeRole(Role role) {
        this.roles.remove(role);
    }

	// Helper methods for complaint
	public void addComplaint(Complaint complain) {
		complaints.add(complain);
		complain.setCitizen(this);
	}
	
	public void removeComplaint(Complaint complain) {
		complaints.remove(complain);
		complain.setCitizen(null);
	}
	
	// helper methods for feedback
	public void addFeedback(Feedback feedback) {
		feedbacks.add(feedback);
		feedback.setCitizen(this);
	}
	
	public Citizen(Citizen citizen) {
		super();
		this.firstName = citizen.getFirstName();
		this.lastName = citizen.getLastName();
		this.username = citizen.getUsername();
		this.email = citizen.getEmail();
		this.password = citizen.getPassword();
		this.complaints = citizen.getComplaints();
		this.feedbacks = citizen.getFeedbacks();
	}
	
//	public void addRole(Role role) {
//        roles.add(role);
//        role.getCitizens().add(this);
//    }
//
//    public void removeRole(Role role) {
//        roles.remove(role);
//        role.getCitizens().remove(this);
//    }
	
	public void removeFeedback(Feedback feedback) {
		feedbacks.remove(feedback);
		feedback.setCitizen(null);
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Complaint> getComplaints() {
		return complaints;
	}

	public void setComplaints(List<Complaint> complaints) {
		this.complaints = complaints;
	}

	public List<Feedback> getFeedbacks() {
		return feedbacks;
	}

	public void setFeedbacks(List<Feedback> feedbacks) {
		this.feedbacks = feedbacks;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	

}
