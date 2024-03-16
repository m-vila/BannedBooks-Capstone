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
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        super();
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(UserDTO userDto) {
        User user = new User();

        user.setName(userDto.getFirstName() + " " +    userDto.getLastName());
        user.setEmail(userDto.getEmail());

        // Encrypt the password using Spring Security
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        Role role = roleRepository.findByName("ROLE_ADMIN");
        if (role == null) {
            role = checkRoleExist();
        }
        user.setRoles(Arrays.asList(role));
        userRepository.save(user);
    }
    private Role checkRoleExist() {
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        return roleRepository.save(role);
    }

    @Override
    public User findUserByEmail(String email) {

        return userRepository.findByEmail(email);
    }
    @Override
    public List<UserDTO> findAllUsers() {
        List<User> users = userRepository.findAll();

        return users.stream()
                .map((user) -> mapToUserDTO(user))
                .collect(Collectors.toList());
    }
    private UserDTO mapToUserDTO(User user) {
        UserDTO userDto = new UserDTO();

        String[] str = user.getName().split(" ");
        userDto.setFirstName(str[0]);
        userDto.setLastName(str[1]);
        userDto.setEmail(user.getEmail());
        return userDto;
    }
}

