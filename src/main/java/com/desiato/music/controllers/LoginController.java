package com.desiato.music.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) throws ServletException {

        // invalidating the session
        request.logout();

        // redirecting to the logout page
        return "redirect:/logout-confirmation";
    }

    @GetMapping("/logout-confirmation")
    public String logoutConfirmation() {
        return "logout";
    }

}
