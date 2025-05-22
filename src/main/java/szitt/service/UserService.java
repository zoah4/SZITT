package szitt.service;

import org.springframework.stereotype.Service;
import szitt.repository.UserRepository;
import szitt.model.User;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repository;
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public Iterable<User> getAllUsers() {
        return this.repository.findAll();
    }

    public Optional<User> getUser(Long id) {
        return this.repository.findById(id);
    }

    public Optional<User> findByEmail(String email) {
        return this.repository.findByEmail(email);
    }

    public void deleteUser(Long id) {
        this.repository.deleteById(id);
    }
}