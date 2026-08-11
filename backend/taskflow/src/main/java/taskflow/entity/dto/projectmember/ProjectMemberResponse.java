package taskflow.entity.dto.projectmember;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import taskflow.entity.enums.ProjectRole;

@Getter
@Setter
@Builder
public class ProjectMemberResponse {
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private ProjectRole role;

}
