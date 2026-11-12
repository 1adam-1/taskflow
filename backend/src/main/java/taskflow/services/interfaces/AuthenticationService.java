package taskflow.services.interfaces;

import taskflow.entity.dto.auth.AuthenticationResponse;
import taskflow.entity.dto.auth.LoginRequest;
import taskflow.entity.dto.auth.RegisterRequest;
import taskflow.entity.dto.auth.UserResponse;

public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse login(LoginRequest request);

    UserResponse getCurrentUser();
}
