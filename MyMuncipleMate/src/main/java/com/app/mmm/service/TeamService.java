package com.app.mmm.service;

import java.util.List;

import com.app.mmm.dto.TeamDTO;

public interface TeamService {

	TeamDTO createTeam(TeamDTO teamDTO);

	TeamDTO getTeamById(Long id);

	List<TeamDTO> getAllTeams();

	TeamDTO updateTeam(Long id, TeamDTO teamDTO);

	void deleteTeam(Long id);
	
	void assignTeamToComplaint(Long complaintId);
}
