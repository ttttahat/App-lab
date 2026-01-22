package taha.labs.project.controller;

import taha.labs.project.model.User;
import taha.labs.project.model.dto.CreateUserRequest;
import taha.labs.project.model.dto.LoginRequest;
import taha.labs.project.service.UserService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(
            @RequestHeader("Content-Type") String ct,
            @Valid @RequestBody CreateUserRequest req
    ) {
        if(!"application/json".equalsIgnoreCase(ct)) {
            return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).build();
        }
        User user = userService.createUser(
                req.getUsername(),
                req.getEmail(),
                req.getPassword()
        );
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestHeader("Content-Type") String ct,
            @Valid @RequestBody LoginRequest req
    ) {
        if(!"application/json".equalsIgnoreCase(ct)) {
            return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).build();
        }
        boolean ok = userService.authenticate(
                req.getUsername(),
                req.getPassword()
        );

        if(ok) return ResponseEntity.ok().build();
        else return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    @GetMapping("/user/{userid}")
    public ResponseEntity<?> getUser(
            @RequestHeader("Content-Type") String ct,
            @PathVariable Long userid
    ) {
        if(!"application/json".equalsIgnoreCase(ct)) {
            return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).build();
        }

        Optional<User> userOpt = userService.getUserById(userid);
        return userOpt.map(user -> ResponseEntity.ok().body(user))
                      .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
