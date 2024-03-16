package org.martavila.bannedbooks;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.martavila.bannedbooks.models.Role;
import org.martavila.bannedbooks.models.User;
import org.martavila.bannedbooks.repositories.UserRepository;
import org.martavila.bannedbooks.repositories.RoleRepository;
import org.martavila.bannedbooks.exceptions.UnableToLoadAdminUsersException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class BannedBooksApplication implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    public static void main(String[] args) {
        SpringApplication.run(BannedBooksApplication.class, args);
    }

    @Override
    public void run(String... args) {
        try {
            initRoles();
            initUsers();
            assignUsersAdminRole();
        } catch (Exception e){
            throw new UnableToLoadAdminUsersException("Unable to load the admin users");
        }
    }

    private void initRoles() throws IOException {

        Role adminRole = roleRepository.findByName("ROLE_ADMIN");

        if (adminRole == null){
            ObjectMapper objectMapper = new ObjectMapper();
            InputStream roleStream = getClass().getResourceAsStream("/roles.json");
            List<Role> roles = objectMapper.readValue(roleStream, new TypeReference<List<Role>>() {});

            roleRepository.saveAll(roles);
        }
    }

    private void initUsers() throws IOException {
        User adminUser = userRepository.findByEmail("admin@admin.com");
        if (adminUser == null){
            ObjectMapper objectMapper = new ObjectMapper();
            InputStream userStream = getClass().getResourceAsStream("/adminUsers.json");
            List<User> users = objectMapper.readValue(userStream, new TypeReference<List<User>>() {});

            userRepository.saveAll(users);
        }
    }

    private void assignUsersAdminRole() {
        User adminUser = userRepository.findByEmail("admin@admin.com");
        Role adminRole = roleRepository.findByName("ROLE_ADMIN");
        adminUser.setRoles(Arrays.asList(adminRole));
        userRepository.save(adminUser);
    }
}
