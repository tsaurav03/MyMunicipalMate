package com.app.mmm.serviceimple;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.app.mmm.dto.AddComplaintDTO;
import com.app.mmm.dto.ApiResponse;
import com.app.mmm.dto.ComplainToBeSHownOnFeedDTO;
import com.app.mmm.dto.ComplaintDTO;
import com.app.mmm.dto.ComplaintToBeShownOnAdminFeed;
import com.app.mmm.entity.Citizen;
import com.app.mmm.entity.Complaint;
import com.app.mmm.enums.ComplaintType;
import com.app.mmm.enums.Status;
import com.app.mmm.exception.ResourceNotFoundException;
import com.app.mmm.repository.CitizenRepository;
import com.app.mmm.repository.ComplaintRepository;
import com.app.mmm.service.ComplaintService;

@Service
@Transactional
public class ComplaintServiceImple implements ComplaintService {

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private ComplaintRepository complaintRepository;

	@Autowired
	private CitizenRepository citizenRepository;

	private final Path fileStorageLocation = Paths.get("uploaded-files").toAbsolutePath().normalize();

	@PostConstruct
	public void init() {
		try {
			Files.createDirectories(fileStorageLocation);
		} catch (Exception ex) {
			throw new RuntimeException("Could not create upload directory", ex);
		}
	}

	@Override
	public ApiResponse addComplaint(MultipartFile file, AddComplaintDTO complaintDTO, String email,
			ComplaintType complaintType) throws ResourceNotFoundException {

		Citizen citizen = citizenRepository.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("Citizen with email " + email + " not found"));

		Complaint complaint = mapper.map(complaintDTO, Complaint.class);
		complaint.setComplaintType(complaintType);
		complaint.setStatus(Status.OPEN);
		complaint.setCitizen(citizen);

		if (file != null && !file.isEmpty()) {
			try {
				byte[] imageData = file.getBytes();
				complaint.setImageData(imageData);
				System.out.println("File uploaded successfully: " + file.getOriginalFilename());
			} catch (Exception ex) {
				throw new RuntimeException("Could not store file", ex);
			}
		}else {
	        System.out.println("No file was uploaded or the file is empty"); 
	    }

		complaintRepository.save(complaint);

		return new ApiResponse("Complaint added successfully");
	}

	@Override
	public ComplaintDTO getComplaintById(Long id) {
	    Complaint complaint = complaintRepository.findById(id)
	            .orElseThrow(() -> new ResourceNotFoundException("Complaint Id not found"));

	    ComplaintDTO complaintDTO = mapper.map(complaint, ComplaintDTO.class);

	    if (complaint.getImageData() != null) {
	        String base64Image = Base64.getEncoder().encodeToString(complaint.getImageData());
	        complaintDTO.setImageData(base64Image); 
	    }

	    return complaintDTO;
	}

	@Override
	public ApiResponse deleteComplaintById(Long id) {
		Complaint complaint = complaintRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Complaint Id not found"));
		complaintRepository.delete(complaint);
		return new ApiResponse("Complaint Deleted with id: " + id);
	}


	@Override
	public List<ComplaintDTO> getComplaintsByStatus(String status) {
		Status complaintStatus = Status.valueOf(status.toUpperCase());
		List<Complaint> complaintDTO = complaintRepository.findByStatus(complaintStatus);
		return complaintDTO.stream().map(dto -> mapper.map(dto, ComplaintDTO.class)).collect(Collectors.toList());
	}

	@Override
	public ApiResponse markComplaintAsResolved(Long id) {
		return changeStatus(id, "RESOLVED");
	}

	@Override
	public ApiResponse markComplaintAsOpen(Long id) {
		return changeStatus(id, "OPEN");
	}

	@Override
	public ApiResponse changeStatus(Long id, String status) {
		try {
			Status newStatus;
			try {
				newStatus = Status.valueOf(status.toUpperCase());
			} catch (IllegalArgumentException e) {
				return new ApiResponse("Invalid status value");
			}

			Complaint complaint = complaintRepository.findById(id)
					.orElseThrow(() -> new RuntimeException("Complaint not found"));

			complaint.setStatus(newStatus);

			complaintRepository.save(complaint);

			return new ApiResponse("Status updated successfully");
		} catch (Exception e) {
			return new ApiResponse(e.getMessage());
		}
	}

	@Override
	public List<ComplainToBeSHownOnFeedDTO> getAllComplaints() {
		return complaintRepository.findAllComplaintsWithDetails().stream()
				.map(complaint -> mapper.map(complaint, ComplainToBeSHownOnFeedDTO.class)).collect(Collectors.toList());
	}
	@Override
    public List<ComplaintToBeShownOnAdminFeed> getAllComplaintsForAdmin() {
        return complaintRepository.findAllComplaintsWithIdForAdminFeed().stream()
                .collect(Collectors.toList());
    }

}
