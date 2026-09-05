package taskflow.mapper;

import org.springframework.stereotype.Component;
import taskflow.entity.ProjectMember;
import taskflow.entity.dto.projectmember.ProjectMemberResponse;

@Component
public class ProjectMemberMapper {
    public ProjectMemberResponse toResponse(ProjectMember projectMember){
        return ProjectMemberResponse.builder()
                .id(projectMember.getUser().getId())
                .firstName(projectMember.getUser().getFirstName())
                .lastName(projectMember.getUser().getLastName())
                .email(projectMember.getUser().getEmail())
                .role(projectMember.getRole())
                .build();
    }
}
