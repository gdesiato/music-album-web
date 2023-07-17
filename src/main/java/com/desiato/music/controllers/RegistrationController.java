package com.desiato.music.controllers;

import com.desiato.music.models.Role;
import com.desiato.music.models.User;
import com.desiato.music.repositories.RoleRepository;
import com.desiato.music.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String registrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        // Create a new role or retrieve an existing "USER" role
        Role userRole = roleRepository.findRoleByName("USER");
        if (userRole == null) {
            userRole = roleRepository.save(new Role("USER"));
        }

        // Assign the "USER" role to the user
        user.setRoles(Collections.singletonList(userRole));

        userService.saveUser(user);
        redirectAttributes.addFlashAttribute("success", "Registration successful");
        return "redirect:/login";
    }
}
