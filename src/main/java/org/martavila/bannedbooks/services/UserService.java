package org.martavila.bannedbooks.services;

import org.martavila.bannedbooks.controllers.dto.UserDTO;
import org.martavila.bannedbooks.models.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDTO userDto);
    User findUserByEmail(String email);
    List<UserDTO> findAllUsers();
}

