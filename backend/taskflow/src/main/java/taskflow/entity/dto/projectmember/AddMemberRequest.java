package taskflow.entity.dto.projectmember;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import taskflow.entity.enums.ProjectRole;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class AddMemberRequest {
    @Email
    @NotBlank
    private String email;
    private ProjectRole role;
}
