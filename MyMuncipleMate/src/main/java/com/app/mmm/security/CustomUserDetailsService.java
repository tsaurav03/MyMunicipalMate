package com.app.mmm.security;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.app.mmm.entity.Citizen;
import com.app.mmm.repository.CitizenRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private CitizenRepository citizenRepository;

	@Override
	public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
		Citizen citizen = citizenRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail).orElseThrow(() -> new UsernameNotFoundException("user Does not Exist by username or email"));
		
		Set<GrantedAuthority> authorities = citizen.getRoles().stream()
				.map((role) -> new SimpleGrantedAuthority(role.getName()))
				.collect(Collectors.toSet());
		
		
		return new User(usernameOrEmail, citizen.getPassword(), authorities);
	}

}
