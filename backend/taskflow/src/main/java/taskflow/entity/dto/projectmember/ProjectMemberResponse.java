package taskflow.entity.dto.projectmember;

import lombok.*;
import taskflow.entity.enums.ProjectRole;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectMemberResponse {
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private ProjectRole role;

}
