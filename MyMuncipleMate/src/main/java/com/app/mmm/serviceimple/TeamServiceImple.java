package com.app.mmm.serviceimple;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.mmm.dto.TeamDTO;
import com.app.mmm.entity.Complaint;
import com.app.mmm.entity.Team;
import com.app.mmm.enums.ComplaintType;
import com.app.mmm.exception.ResourceNotFoundException;
import com.app.mmm.repository.ComplaintRepository;
import com.app.mmm.repository.TeamRepository;
import com.app.mmm.service.TeamService;

@Service
@Transactional
public class TeamServiceImple implements TeamService {

	@Autowired
    private TeamRepository teamRepository;
	
	@Autowired
	private ComplaintRepository complaintRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public TeamDTO createTeam(TeamDTO teamDTO) {
        Team team = modelMapper.map(teamDTO, Team.class);
        Team savedTeam = teamRepository.save(team);
        return modelMapper.map(savedTeam, TeamDTO.class);
    }

    @Override
    public TeamDTO getTeamById(Long id) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Team not found with ID: " + id));
        return modelMapper.map(team, TeamDTO.class);
    }

    @Override
    public List<TeamDTO> getAllTeams() {
        return teamRepository.findAll().stream()
                .map(team -> modelMapper.map(team, TeamDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public TeamDTO updateTeam(Long id, TeamDTO teamDTO) {
        Team existingTeam = teamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Team not found with ID: " + id));

        existingTeam.setName(teamDTO.getName());
        Team updatedTeam = teamRepository.save(existingTeam);
        return modelMapper.map(updatedTeam, TeamDTO.class);
    }

    @Override
    public void deleteTeam(Long id) {
        if (!teamRepository.existsById(id)) {
            throw new ResourceNotFoundException("Team not found with ID: " + id);
        }
        teamRepository.deleteById(id);
    }

    @Override
    public void assignTeamToComplaint(Long complaintId) {
        Complaint complaint = complaintRepository.findById(complaintId)
                .orElseThrow(() -> new ResourceNotFoundException("Complaint not found with ID: " + complaintId));

        Team team = getTeamForComplaintType(complaint.getComplaintType());
        
        complaint.setTeam(team);
        complaintRepository.save(complaint);
    }

    private Team getTeamForComplaintType(ComplaintType complaintType) {
        switch (complaintType) {
            case GARBAGE_MANAGEMENT:
                return teamRepository.findByName("Waste_Management_Team")
                        .orElseThrow(() -> new ResourceNotFoundException("Team not found for Garbage Management"));
            case WATER_SUPPLY:
                return teamRepository.findByName("Water_Supply_Team")
                        .orElseThrow(() -> new ResourceNotFoundException("Team not found for Water Supply"));
            case ELECTRICITY_MANAGEMENT:
                return teamRepository.findByName("Electricity_Maintenance_Team")
                        .orElseThrow(() -> new ResourceNotFoundException("Team not found for Electricity Management"));
            case ROAD_REPAIR:
                return teamRepository.findByName("Road_Repair_Team")
                        .orElseThrow(() -> new ResourceNotFoundException("Team not found for Road Repair"));
            case SANITATION_ISSUES:
                return teamRepository.findByName("Sanitation_and_Hygiene_Team")
                        .orElseThrow(() -> new ResourceNotFoundException("Team not found for Sanitation Issues"));
            case TRAFFIC_MANAGEMENT:
                return teamRepository.findByName("Traffic_Control_Team")
                        .orElseThrow(() -> new ResourceNotFoundException("Team not found for Traffic Management"));
            case ENVIRONMENTAL_HAZARDS:
                return teamRepository.findByName("Environmental_Protection_Team")
                        .orElseThrow(() -> new ResourceNotFoundException("Team not found for Environmental Hazards"));
            case FIRE_SAFETY:
                return teamRepository.findByName("Fire_Safety_and_Inspection_Team")
                        .orElseThrow(() -> new ResourceNotFoundException("Team not found for Fire Safety"));
            default:
                throw new IllegalArgumentException("Unknown Complaint Type: " + complaintType);
        }
    }

}
