package taha.labs.project.controller;

import taha.labs.project.model.dto.CreateUserRequest;
import taha.labs.project.service.UserService;

import jakarta.validation.Valid;
import org.springframework.security.core.Authentication; 
import org.springframework.security.core.context.SecurityContextHolder; 
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !auth.getPrincipal().equals("anonymousUser")) {
            return "redirect:/user/home";
        }

        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new CreateUserRequest());
        }
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(
            @Valid @ModelAttribute("user") CreateUserRequest req,
            BindingResult result, 
            RedirectAttributes redirectAttrs,
            Model model
    ) {
        if (result.hasErrors()) {
            return "register";
        }

        try {
            userService.createUser(
                    req.getUsername(),
                    req.getEmail(),
                    req.getPassword()
            );
        } catch (IllegalArgumentException ex) {
            model.addAttribute("registrationError", ex.getMessage());
            return "register";
        }

        redirectAttrs.addFlashAttribute(
                "successMessage",
                "Registered successfully! Please login."
        );

        return "redirect:/login";
    }
}