package jp.co.axa.apidemo.controllers;

import io.swagger.annotations.*;
import jp.co.axa.apidemo.entities.User;
import jp.co.axa.apidemo.models.AuthRequest;
import jp.co.axa.apidemo.models.AuthResponse;
import jp.co.axa.apidemo.repositories.UserRepository;
import jp.co.axa.apidemo.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for user authentication and registration.
 * These endpoints are publicly accessible (no authentication required).
 */
@Api(tags = "Authentication", description = "APIs for user authentication and registration")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    /**
     * Authenticates a user and returns a JWT token.
     * The token should be included in the Authorization header for subsequent requests.
     *
     * @param loginRequest The login credentials (username and password)
     * @return JWT token if authentication is successful
     */
    @ApiOperation(value = "User login", notes = "Authenticates a user and returns a JWT token")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully authenticated and returned JWT token"),
        @ApiResponse(code = 401, message = "Invalid username or password")
    })
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @ApiParam(value = "Login credentials", required = true)
            @RequestBody AuthRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwt = jwtTokenUtil.generateToken(userDetails);

        AuthResponse response = new AuthResponse();
        response.setToken(jwt);
        return ResponseEntity.ok(response);
    }

    /**
     * Registers a new user in the system.
     * The user will be assigned the default "USER" role.
     *
     * @param registerRequest The registration details (username and password)
     * @return Success message if registration is successful
     */
    @ApiOperation(value = "User registration", notes = "Registers a new user in the system")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully registered the user"),
        @ApiResponse(code = 400, message = "Username is already taken")
    })
    @PostMapping("/register")
    public ResponseEntity<String> register(
            @ApiParam(value = "Registration details", required = true)
            @RequestBody AuthRequest registerRequest) {
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            return ResponseEntity.badRequest().body("Username is already taken!");
        }

        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole("USER"); // Default role

        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully!");
    }
} 