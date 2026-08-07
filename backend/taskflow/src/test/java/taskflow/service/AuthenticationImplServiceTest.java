package taskflow.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import taskflow.entity.User;
import taskflow.entity.dto.auth.AuthenticationResponse;
import taskflow.entity.dto.auth.RegisterRequest;
import taskflow.exception.EmailAlreadyExistsException;
import taskflow.repository.UserRepository;
import taskflow.security.jwt.JwtService;
import taskflow.services.impl.AuthenticationServiceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationImplServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticatonManaager;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    @Test
    void should_register_successfully(){
        RegisterRequest request = new RegisterRequest(
                "Adam", "Test", "adam@test.com", "Password123"
        );

        when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(any())).thenReturn("HashedPassword");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(jwtService.generateToken(any(User.class))).thenReturn("jwt-token");

        AuthenticationResponse response = authenticationService.register(request);

        assertNotNull(response);
        assertEquals("jwt-token",response.getAccessToken());

        verify(userRepository).save(any(User.class));

    }

    @Test
    void should_throw_exception_when_email_exists() {

        RegisterRequest request = new RegisterRequest(
                "Adam",
                "Test",
                "adam@test.com",
                "Password123"
        );

        when(userRepository.existsByEmail(request.getEmail()))
                .thenReturn(true);

        assertThrows(
                EmailAlreadyExistsException.class,
                () -> authenticationService.register(request)
        );

        verify(userRepository, never()).save(any(User.class));
        verify(passwordEncoder, never()).encode(any());
        verify(jwtService, never()).generateToken(any(User.class));
    }

}
