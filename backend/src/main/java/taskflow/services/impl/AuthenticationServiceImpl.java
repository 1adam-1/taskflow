package taskflow.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import taskflow.entity.User;
import taskflow.entity.dto.auth.AuthenticationResponse;
import taskflow.entity.dto.auth.LoginRequest;
import taskflow.entity.dto.auth.RegisterRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import taskflow.entity.enums.Role;
import taskflow.exception.EmailAlreadyExistsException;
import taskflow.exception.UserNotFoundException;
import taskflow.repository.UserRepository;
import taskflow.security.jwt.JwtService;
import taskflow.services.interfaces.AuthenticationService;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse register(RegisterRequest request){
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException(request.getEmail());
        }

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .enabled(true)
                .role(Role.ROLE_USER)
                .build();

        userRepository.save(user);
        String jwt = jwtService.generateToken(user);
        return AuthenticationResponse.builder().accessToken(jwt).build();
    }

    @Override
    public AuthenticationResponse login(LoginRequest request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new UserNotFoundException("User not found")
        );
        String jwt = jwtService.generateToken(user);
        return AuthenticationResponse.builder().accessToken(jwt).build();
    }
}
