package com.app.mmm.config;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.app.mmm.entity.Citizen;
import com.app.mmm.entity.Role;
import com.app.mmm.exception.ResourceNotFoundException;
import com.app.mmm.repository.CitizenRepository;
import com.app.mmm.repository.RoleRepository;
import com.app.mmm.security.JwtTokenProvider;

@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CitizenRepository citizenRepository;
    
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        String email = getEmailFromOAuth2Token(token);
        

        if (citizenRepository.findByEmail(email).isPresent()) {
            System.out.println("Existing user logged in: " + email);
        } else {
            Citizen citizen = new Citizen();
            citizen.setFirstName(getFirstNameFromOAuth2Token(token));
            citizen.setLastName(getLastNameFromOAuth2Token(token));
            citizen.setEmail(email);
            
            System.out.println("Fetching role...");
            Role citizenRole = roleRepository.findByName("ROLE_CITIZEN")
                    .orElseThrow(() -> new ResourceNotFoundException("ROLE NOT FOUND"));
            System.out.println("Role found: " + citizenRole.getName());

            Set<Role> roles = new HashSet<>();
            roles.add(citizenRole);
            citizen.setRoles(roles);

            System.out.println("Roles assigned to citizen: " + citizen.getRoles());

            citizenRepository.save(citizen);

            Citizen savedCitizen = citizenRepository.findByEmail("testuser@example.com").orElse(null);
            
            System.out.println(savedCitizen);

        }

        String jwtToken = jwtTokenProvider.generateToken(authentication);

        Cookie cookie = new Cookie("jwtToken", jwtToken);
        cookie.setHttpOnly(false);
        cookie.setSecure(false); 
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60); 
        
        response.addCookie(cookie);

        String redirectUrl = "http://localhost:3000/complain";
        System.out.println("Redirecting to " + redirectUrl);
        redirectStrategy.sendRedirect(request, response, redirectUrl);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                        Authentication authentication) throws IOException, ServletException {
        AuthenticationSuccessHandler.super.onAuthenticationSuccess(request, response, chain, authentication);
    }

    private String getEmailFromOAuth2Token(OAuth2AuthenticationToken token) {
        String email = token.getPrincipal().getAttribute("email");
        if (email == null) {
            email = token.getPrincipal().getAttribute("login");
        }
        return email;
    }

    private String getFirstNameFromOAuth2Token(OAuth2AuthenticationToken token) {
        String firstName = token.getPrincipal().getAttribute("given_name");
        if (firstName == null) {
            firstName = token.getPrincipal().getAttribute("name");
            if (firstName != null && firstName.contains(" ")) {
                firstName = firstName.split(" ")[0];
            }
        }
        return firstName;
    }

    private String getLastNameFromOAuth2Token(OAuth2AuthenticationToken token) {
        String lastName = token.getPrincipal().getAttribute("family_name");
        if (lastName == null) {
            String fullName = token.getPrincipal().getAttribute("name");
            if (fullName != null && fullName.contains(" ")) {
                lastName = fullName.substring(fullName.indexOf(" ") + 1);
            }
        }
        return lastName;
    }
}
