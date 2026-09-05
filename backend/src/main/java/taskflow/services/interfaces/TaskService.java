package taskflow.services.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import taskflow.entity.Task;
import taskflow.entity.dto.task.CreateTaskRequest;
import taskflow.entity.dto.task.TaskResponse;

import java.util.List;

public interface TaskService {
     TaskResponse createTask(Long projectId, CreateTaskRequest request);
     List<TaskResponse> getTasksByProject(Long projectId);
     TaskResponse updateTask(Long projectId, CreateTaskRequest request);
     TaskResponse getTaskById(Long taskId);
     void delete(Long taskId);
     List<TaskResponse> getTasksByAssignee();
}
