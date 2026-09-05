package taskflow.mapper;

import org.springframework.stereotype.Component;
import taskflow.entity.Project;
import taskflow.entity.dto.project.ProjectResponse;

@Component
public class ProjectMapper {
    public ProjectResponse toResponse(Project project){
        return ProjectResponse.builder().id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .startDate(project.getStartDate())
                .endDate(project.getEndDate())
                .status(project.getStatus())
                .ownerName(
                        project.getOwner().getFirstName()
                                + " "
                                + project.getOwner().getLastName()
                )
                .build();
    }
}
