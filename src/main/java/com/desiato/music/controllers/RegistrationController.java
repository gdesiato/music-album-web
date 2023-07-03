package com.desiato.music.controllers;

import com.desiato.music.models.User;
import com.desiato.music.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("register")
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String registrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping
    public String registerUser(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        userService.save(user);  // handle saving user to the database
        redirectAttributes.addFlashAttribute("success", "Registration successful");
        return "redirect:/login";
    }
}
