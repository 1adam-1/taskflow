package taskflow.entity.dto.auth;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class AuthenticationResponse {
    private String accessToken;
}
