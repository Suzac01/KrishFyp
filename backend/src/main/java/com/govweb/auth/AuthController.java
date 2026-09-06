package com.govweb.auth;

import com.govweb.security.JwtService;
import com.govweb.user.Role;
import com.govweb.user.User;
import com.govweb.user.UserRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserRepository users;
    private final PasswordEncoder encoder;
    private final JwtService jwt;

    public AuthController(UserRepository users, PasswordEncoder encoder, JwtService jwt) {
        this.users = users;
        this.encoder = encoder;
        this.jwt = jwt;
    }

    public record Register(
            @NotBlank String fullName,
            @Email @NotBlank String email,
            @NotBlank String mobile,
            String address,
            @Size(min = 8) String password) {}

    public record Login(@Email String email, @NotBlank String password) {}

    public record Response(String token, Long id, String fullName, String email, Role role) {}

    @PostMapping("/register")
    public Response register(@Valid @RequestBody Register request) {
        if (users.findByEmail(request.email()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already registered");
        }

        var user = new User();
        user.setFullName(request.fullName());
        user.setEmail(request.email());
        user.setMobile(request.mobile());
        user.setAddress(request.address());
        user.setPassword(encoder.encode(request.password()));
        return response(users.save(user));
    }

    @PostMapping("/login")
    public Response login(@Valid @RequestBody Login request) {
        User user = users.findByEmail(request.email())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials"));

        if (!encoder.matches(request.password(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }

        return response(user);
    }

    private Response response(User user) {
        return new Response(
                jwt.create(user.getEmail(), user.getRole().name()),
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getRole());
    }
}
