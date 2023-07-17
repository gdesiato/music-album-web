package com.desiato.music.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import com.desiato.music.models.Role;
import com.desiato.music.models.User;
import com.desiato.music.repositories.RoleRepository;
import com.desiato.music.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@SpringBootTest
public class RegistrationControllerTest {

    // Declare mocks for dependencies

    @Mock
    private UserService userService;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private Model model;

    @Mock
    private RedirectAttributes redirectAttributes;

    @InjectMocks
    private RegistrationController registrationController;

    private User user;

    // This method sets up the test environment before each test method
    @BeforeEach
    public void setUp() {
        user = new User();
        user.setUsername("test");
        user.setPassword("password");
    }

    // This test case tests the method for showing the registration form
    @Test
    public void testRegistrationForm() {
        String view = registrationController.registrationForm(model);
        verify(model).addAttribute("user", new User());
        assertEquals("register", view);
    }

    // This test case tests the method for registering a new user
    @Test
    public void testRegisterUser() {
        // Define the behavior of mocked methods
        when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");
        when(roleRepository.findRoleByName("USER")).thenReturn(new Role("USER"));
        when(userService.saveUser(any(User.class))).thenReturn(user);

        // Call the method under test
        String view = registrationController.registerUser(user, redirectAttributes);

        // Verify that the method called the correct dependency methods
        verify(userService).saveUser(user);
        verify(redirectAttributes).addFlashAttribute("success", "Registration successful");

        // Assert that the method returned the correct view name
        assertEquals("redirect:/login", view);
    }
}
