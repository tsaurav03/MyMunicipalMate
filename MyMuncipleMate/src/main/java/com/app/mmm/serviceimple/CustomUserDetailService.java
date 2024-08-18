package com.app.mmm.serviceimple;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.app.mmm.entity.Citizen;
import com.app.mmm.repository.CitizenRepository;

@Service
public class CustomUserDetailService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    @Autowired
    private CitizenRepository repository;
    
    @Autowired
    private PasswordEncoder encoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        Map<String, Object> attributes = oAuth2User.getAttributes();
        String email = (String) attributes.get("email");

        if (email == null) {
            email = (String) attributes.get("login");
        }

        Optional<Citizen> userOptional = repository.findByEmail(email);
        Citizen user;

        if (userOptional.isPresent()) {
            user = userOptional.get();
        } else {
            if (email != null) {
                user = new Citizen();
                user.setEmail(email);
                user.setFirstName((String) attributes.get("given_name"));
                user.setLastName((String) attributes.get("family_name"));

                user.setUsername(email);

               
                user.setPassword(encoder.encode("default_password")); 

                repository.save(user);
            } else {
                throw new OAuth2AuthenticationException("Email and Login not provided by OAuth2 provider");
            }
        }

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_CITIZEN")),
                attributes,
                "email"
        );
    }
}
