package taskflow.entity.dto.task;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import taskflow.entity.enums.Priority;
import taskflow.entity.enums.TaskStatus;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class TaskResponse {

    private Long id;

    private String title;

    private String description;

    private TaskStatus status;

    private Priority priority;

    private LocalDate dueDate;

    private Long projectId;

    private Long assigneeId;

    private String assigneeName;

}
