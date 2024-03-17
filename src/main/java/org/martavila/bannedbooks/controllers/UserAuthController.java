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

    //Method to handle the home (index.html is home) page request
    @GetMapping("/index")
    public String home() {

        return "index";
    }

    //Method handles the login request
    @GetMapping("/login")
    public String login() {

        return "login";
    }

    //Method to handle the users registration form request
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {

        UserDTO user = new UserDTO();
        model.addAttribute("user", user);

        return "register";
    }

    //Method to handle user registration from submit request
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDTO userDto, BindingResult result,
                               Model model) {
        User existingUser = userService.findUserByEmail(userDto.getEmail());

        if (existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()) {
            result.rejectValue("email", null, "There is already an account registered with the same email");
        }

        if (result.hasErrors()) {
            model.addAttribute("user", userDto);

            return "/register";
        }

        userService.saveUser(userDto);

        return "redirect:/register?success";

    }

    //Method is used to handle a list of users
    @GetMapping("/admin-dashboard")
    public String users(Model model) {
        List<UserDTO> users = userService.findAllUsers();

        model.addAttribute("users", users);

        return "admin-dashboard";

    }

}

