package taha.labs.project.repository;

import taha.labs.project.model.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    long countByEmail(String email);

    long countByUsername(String username);
}
