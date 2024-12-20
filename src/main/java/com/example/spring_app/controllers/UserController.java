package com.example.spring_app.controllers;

import com.example.spring_app.dtos.TeamDTO;
import com.example.spring_app.dtos.UserDTO;
import com.example.spring_app.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("user", new UserDTO());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") UserDTO userDTO, Model model) {
        String result = userService.registerUser(userDTO);
        if (result.equals("Uživatel úspěšně registrován.")) {
            return "redirect:/login";
        } else {
            model.addAttribute("error", result);
            return "register";
        }
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }


    @PostMapping("/login")
    public String loginUser(@ModelAttribute("user") UserDTO userDTO, Model model) {
        boolean isAuthenticated = userService.loginUser(userDTO.getUsername(), userDTO.getPassword());
        if (isAuthenticated) {
            return "redirect:/";
        } else {
            model.addAttribute("error", "Neplatné přihlašovací údaje.");
            return "login";
        }
    }
}
