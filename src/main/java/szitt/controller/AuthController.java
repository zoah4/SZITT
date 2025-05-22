package szitt.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import szitt.dto.LoginDTO;
import szitt.dto.RegisterDTO;
import szitt.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public void register(@RequestBody RegisterDTO dto) {
        authService.register(dto);
    }

    @PostMapping("/login")
    public void login(@RequestBody LoginDTO dto) {
        authService.login(dto);
    }
}

