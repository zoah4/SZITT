package szitt.controller;

import org.springframework.web.bind.annotation.*;
import szitt.service.UserService;
import szitt.model.User;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService service;
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public Iterable<User> getAllUsers() {
        return this.service.getAllUsers();
    }

    @GetMapping("/{id}")
    public Optional<User> getUser(@PathVariable Long id) {
        return this.service.getUser(id);
    }

    @GetMapping("/email/{emailValue}")
    public Optional<User> findByEmail(@PathVariable String emailValue) {
        return this.service.findByEmail(emailValue);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        this.service.deleteUser(id);
    }



    /*@PutMapping
    public Optional<User> editProfile(@PathVariable Long id, UserDTO dto) {
        return this.service.getUser(id);
    }*/
}