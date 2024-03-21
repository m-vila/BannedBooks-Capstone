package org.martavila.bannedbooks;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.martavila.bannedbooks.models.User;
import org.martavila.bannedbooks.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserRepositoryTests {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByEmail() {
        // Arrange
        String userEmail = "admin@admin.com";

        // Act
        User foundUser = userRepository.findByEmail(userEmail);

        // Assert
        Assertions.assertNotNull(foundUser);
        Assertions.assertEquals(userEmail, foundUser.getEmail());
    }
}
