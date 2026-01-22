package taha.labs.project.controller;

import jakarta.validation.Valid;
import taha.labs.project.model.dto.CreateUserRequest;
import taha.labs.project.model.dto.LoginRequest;
import taha.labs.project.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Existing endpoints assumed unchanged
}
