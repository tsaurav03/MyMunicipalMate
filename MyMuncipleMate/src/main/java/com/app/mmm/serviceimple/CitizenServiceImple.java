package com.app.mmm.serviceimple;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.mmm.dto.AddComplaintDTO;
import com.app.mmm.dto.ApiResponse;
import com.app.mmm.dto.CitizenDto;
import com.app.mmm.dto.CitizenDtoWithComplaints;
import com.app.mmm.dto.CitizenSummaryDto;
import com.app.mmm.dto.RoleAssignmentDTO;
import com.app.mmm.entity.Citizen;
import com.app.mmm.entity.Complaint;
import com.app.mmm.entity.Role;
import com.app.mmm.exception.ResourceNotFoundException;
import com.app.mmm.repository.CitizenRepository;
import com.app.mmm.repository.RoleRepository;
import com.app.mmm.service.CitizenService;

@Service
@Transactional
public class CitizenServiceImple implements CitizenService {

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private CitizenRepository repo;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder encoder;

	@Override
	public CitizenDtoWithComplaints getCitizen(Long id) {
		Citizen citizen = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("CITIZEN ID NOT FOUND"));
		CitizenDtoWithComplaints citizenDto = mapper.map(citizen, CitizenDtoWithComplaints.class);
		return citizenDto;
	}

	@Override
	public ApiResponse deleteCitizenByID(Long id) {
		Citizen citizenToBeDeleted = repo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("CITIZEN ID NOT FOUND"));
		repo.delete(citizenToBeDeleted);
		return new ApiResponse("Citizen Deleted With Id: " + id);
	}

	@Override
	public List<AddComplaintDTO> getAllComplaintsByCitizen(Long id) {
		Citizen citizen = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Citizen ID NOT FOUND"));

		List<Complaint> complaints = citizen.getComplaints();

		return complaints.stream().map(complaint -> mapper.map(complaint, AddComplaintDTO.class))
				.collect(Collectors.toList());
	}

	@Override
	public CitizenDto updateCitizenById(Long id, CitizenDto citizenDto) {
		Citizen citizen = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Citizen ID NOT FOUND"));

		if (citizenDto.getPassword().equals(citizenDto.getConfirmPassword())) {
			mapper.map(citizenDto, citizen);
			return citizenDto;
		}
		throw new ResourceNotFoundException("Passwords don't match");
	}

	@Override
	public List<CitizenSummaryDto> getAllCitizen() {
		List<CitizenSummaryDto> citizenDto = repo.findAll().stream().map(i -> mapper.map(i, CitizenSummaryDto.class))
				.collect(Collectors.toList());
		return citizenDto;
	}

	@Override
	public ApiResponse addRoleToCitizen(RoleAssignmentDTO roleAssignmentDTO) {
		Citizen citizen = repo.findByEmail(roleAssignmentDTO.getEmail())
				.orElseThrow(() -> new ResourceNotFoundException(
						"User with email: " + roleAssignmentDTO.getEmail() + " doesn't exist"));

		Role role = roleRepository.findByName(roleAssignmentDTO.getRoleName()).orElseThrow(
				() -> new ResourceNotFoundException("Role: " + roleAssignmentDTO.getRoleName() + " does not exist"));

		citizen.addRole(role);
		repo.save(citizen);

		return new ApiResponse("Role added successfully");
	}

	@Override
	public CitizenDto updatePartialCitizenDetails(Long citizenId, Map<String, Object> updates) {
		Citizen citizen = repo.findById(citizenId)
				.orElseThrow(() -> new ResourceNotFoundException("Invalid Citizen ID"));

		if (updates.containsKey("password")) {
			String newPassword = (String) updates.get("password");
			String encryptedPassword = encoder.encode(newPassword);
			citizen.setPassword(encryptedPassword);
			updates.remove("password");
		}

		mapper.map(updates, citizen);
		Citizen updatedCitizen = repo.save(citizen);
		return mapper.map(updatedCitizen, CitizenDto.class);
	}

}
