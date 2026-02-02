package taha.labs.project.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import taha.labs.project.validator.ValidUsername;

public class CreateUserRequest {

    @NotBlank
    @Size(min = 3, max = 20,  message = "Username is minimum 3 characters and maximum 20 characters.")
    private String username;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 6, message = "Password is too short, please use at least 6 characters.")
    private String password;

    public CreateUserRequest() {}

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
