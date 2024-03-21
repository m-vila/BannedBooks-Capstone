package org.martavila.bannedbooks;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.martavila.bannedbooks.models.Role;
import org.martavila.bannedbooks.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RoleRepositoryTests {
    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void testFindByName() {
        // Arrange
        String roleName = "ROLE_ADMIN";

        // Act
        Role foundRole = roleRepository.findByName(roleName);

        // Assert
        Assertions.assertNotNull(foundRole);
        Assertions.assertEquals(roleName, foundRole.getName());
    }
}
