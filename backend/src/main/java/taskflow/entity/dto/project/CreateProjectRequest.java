package taskflow.entity.dto.project;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import taskflow.entity.enums.ProjectStatus;

import java.time.LocalDate;

@Getter
@Setter
public class CreateProjectRequest {
    @NotBlank
    @Size(max=100)
    private String name;

    private String description;

    @NotNull
    private LocalDate startDate;

    private LocalDate endDate;

    private ProjectStatus status;

}
