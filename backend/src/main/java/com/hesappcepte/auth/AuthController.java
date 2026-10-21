package com.hesappcepte.auth;

import com.hesappcepte.auth.dto.AuthResponse;
import com.hesappcepte.auth.dto.LoginRequest;
import com.hesappcepte.auth.dto.RegisterRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.Authentication;
import java.util.Map;



@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@Valid @RequestBody RegisterRequest request) {
        authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);

    }

    @GetMapping("/me")
    public Map<String, Long> me(Authentication authentication) {

        Long userId = (Long) authentication.getPrincipal();

        return Map.of("userId", userId);
    }
}




