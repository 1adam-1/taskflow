package taskflow.entity.dto.task;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import taskflow.entity.enums.Priority;
import taskflow.entity.enums.TaskStatus;

import java.time.LocalDate;

@Getter
@Setter
public class CreateTaskRequest {
    @NotBlank
    @Size(max = 150)
    private String title;

    @Size(max = 2000)
    private String description;

    private TaskStatus status;

    private Priority priority;

    private LocalDate dueDate;

    private Long assigneeId;
}
