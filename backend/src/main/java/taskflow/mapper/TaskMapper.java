package taskflow.mapper;

import org.springframework.stereotype.Component;
import taskflow.entity.Task;
import taskflow.entity.dto.task.TaskResponse;

@Component
public class TaskMapper {
    public TaskResponse toResponse(Task task) {

        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .priority(task.getPriority())
                .dueDate(task.getDueDate())
                .projectId(task.getProject().getId())
                .assigneeId(
                        task.getAssignee() != null
                                ? task.getAssignee().getId()
                                : null
                )
                .assigneeName(
                        task.getAssignee() != null
                                ? task.getAssignee().getFirstName()
                                + " "
                                + task.getAssignee().getLastName()
                                : null
                )
                .build();
    }
}
