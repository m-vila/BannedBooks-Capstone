package org.martavila.bannedbooks;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.martavila.bannedbooks.controllers.dto.UserDTO;
import org.martavila.bannedbooks.models.Role;
import org.martavila.bannedbooks.models.User;
import org.martavila.bannedbooks.repositories.RoleRepository;
import org.martavila.bannedbooks.repositories.UserRepository;
import org.martavila.bannedbooks.services.impl.UserServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserServiceImplTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void testSaveUser() {
        // Arrange
        UserDTO userDto = new UserDTO();
        userDto.setFirstName("Marta");
        userDto.setLastName("Vila");
        userDto.setEmail("mvila@example.com");
        userDto.setPassword("password123");

        when(roleRepository.findByName("ROLE_USER")).thenReturn(new Role());
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");

        // Act
        userService.saveUser(userDto);

        // Assert
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testFindAllUsers() {
        // Arrange
        User user1 = new User();
        user1.setName("Marta Vila");
        user1.setEmail("mvila@example.com");

        User user2 = new User();
        user2.setName("Lola Fernandez");
        user2.setEmail("jf@example.com");

        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        // Act
        List<UserDTO> userDTOs = userService.findAllUsers();

        // Assert
        Assertions.assertEquals(2, userDTOs.size());
        Assertions.assertEquals("Marta", userDTOs.get(0).getFirstName());
        Assertions.assertEquals("Vila", userDTOs.get(0).getLastName());
        Assertions.assertEquals("mvila@example.com", userDTOs.get(0).getEmail());
        Assertions.assertEquals("Lola", userDTOs.get(1).getFirstName());
        Assertions.assertEquals("Fernandez", userDTOs.get(1).getLastName());
        Assertions.assertEquals("jf@example.com", userDTOs.get(1).getEmail());
    }
}
