package org.martavila.bannedbooks.services.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.martavila.bannedbooks.controllers.dto.UserDTO;
import org.martavila.bannedbooks.models.Role;
import org.martavila.bannedbooks.models.User;
import org.martavila.bannedbooks.repositories.RoleRepository;
import org.martavila.bannedbooks.repositories.UserRepository;
import org.martavila.bannedbooks.services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        super();
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(UserDTO userDto) {

        // Create a new User object and populate it with userDto data
        User user = new User();
        user.setName(userDto.getFirstName() + " " +    userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        // Retrieve the "ROLE_USER" role from the database and assign it by default to any new user that registers
        Role role = roleRepository.findByName("ROLE_USER");
        user.setRoles(Arrays.asList(role));

        // Persist the new user to the database
        userRepository.save(user);
    }

    @Override
    public User findUserByEmail(String email) {

        // Find a user by their email
        return userRepository.findByEmail(email);
    }
    @Override
    public List<UserDTO> findAllUsers() {

        // Retrieve all users from the database and map them to UserDTOs
        List<User> users = userRepository.findAll();
        return users.stream()
                .map((user) -> mapToUserDTO(user))
                .collect(Collectors.toList());
    }
    private UserDTO mapToUserDTO(User user) {

        // Map a User object to a UserDTO
        UserDTO userDto = new UserDTO();
        String[] str = user.getName().split(" ");
        userDto.setFirstName(str[0]);
        userDto.setLastName(str[1]);
        userDto.setEmail(user.getEmail());

        return userDto;
    }
}

