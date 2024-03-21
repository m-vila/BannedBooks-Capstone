package org.martavila.bannedbooks.controllers;

import java.util.List;

import org.martavila.bannedbooks.controllers.dto.UserDTO;
import org.martavila.bannedbooks.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.validation.Valid;
import org.martavila.bannedbooks.services.UserService;

@Controller
public class UserAuthController {
    private UserService userService;

    @Autowired
    public UserAuthController(UserService userService)    {

        this.userService = userService;
    }

    // Method to handle the home page request
    @GetMapping("/index")
    public String home() {

        return "index";
    }

    // Method to handle the login request
    @GetMapping("/login")
    public String login() {

        return "login";
    }

    // Method to handle the users registration form request
    @GetMapping("/user-registration")
    public String showRegistrationForm(Model model) {

        UserDTO user = new UserDTO();
        model.addAttribute("user", user);

        return "user-registration";
    }

    // Method to handle user registration form submission
    @PostMapping("/user-registration/save")
    public String registration(@Valid @ModelAttribute("user") UserDTO userDto, BindingResult result,
                               Model model) {

        User existingUser = userService.findUserByEmail(userDto.getEmail());

        // Check if user with the same email already exists
        if (existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()) {
            result.rejectValue("email", null, "There is already an account registered under the same email");
        }

        // If there are validation errors, return to the registration form with error messages
        if (result.hasErrors()) {
            model.addAttribute("user", userDto);

            return "user-registration";
        }

        // Save the user if no validation errors
        userService.saveUser(userDto);

        return "redirect:/user-registration?success";

    }

    // Method to show the user dashboard
    @GetMapping("/user-dashboard")
    public String showUserDashboard() {

        return "user-dashboard";
    }

    // Method to show the admin dashboard
    @GetMapping("/admin-dashboard")
    public String showAdminDashboard() {

        return "admin-dashboard";
    }

    // Method to handle displaying the list of registered users
    @GetMapping("/registered-users")
    public String users(Model model) {
        List<UserDTO> users = userService.findAllUsers();

        model.addAttribute("users", users);

        return "registered-users";
    }

}

