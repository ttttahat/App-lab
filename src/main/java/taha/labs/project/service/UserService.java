package taha.labs.project.service;

import taha.labs.project.model.User;
import taha.labs.project.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // creating new user
    public User createUser(String username, String email, String password) {
        User user = new User(username, email, password);
        return userRepository.save(user);
    }

    // authenticating the user
    public boolean authenticate(String email, String password) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        return userOpt
                .map(user -> user.getPassword().equals(password))
                .orElse(false);
    }
}
