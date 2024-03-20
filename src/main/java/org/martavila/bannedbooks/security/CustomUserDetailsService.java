package org.martavila.bannedbooks.security;

import org.martavila.bannedbooks.models.User;
import org.martavila.bannedbooks.repositories.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;
    public CustomUserDetailsService(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        // Retrieve user information from the UserRepository based on the provided email
        User user = userRepository.findByEmail(email);

        if(user != null) {
            // If the user exists, create a UserDetails object with the user's details
            return new org.springframework.security.core.userdetails.User(user.getEmail(),
                    user.getPassword(),
                    user.getRoles().stream()
                            .map((role) -> new SimpleGrantedAuthority(role.getName()))
                            .collect(Collectors.toList()));
        } else {
            // If the user does not exist, throw an exception indicating invalid credentials
            throw new UsernameNotFoundException("Invalid email or password");
        }
    }
}

