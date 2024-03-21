package org.martavila.bannedbooks.controllers.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private Long id;

    @Pattern(regexp = "[A-Za-z ]+$", message = "Only alphabetic allowed")
    @NotEmpty(message = "Please write your name")
    private String firstName;

    @Pattern(regexp = "[A-Za-z ]+$", message = "Only alphabetic allowed")
    private String lastName;

    @NotEmpty(message = "Email should not be empty")
    @Email
    private String email;

    @NotEmpty(message = "Password should not be empty")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(regexp = "^(?=.*[A-Z]).*$", message = "Password must contain at least one uppercase letter")
    private String password;
}

