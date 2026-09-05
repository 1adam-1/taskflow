package taskflow.entity.dto.project;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import taskflow.entity.enums.ProjectStatus;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class ProjectResponse {

    private Long id;

    private String name;

    private String description;

    private LocalDate startDate;

    private LocalDate endDate;

    private ProjectStatus status;

    private String ownerName;
}
