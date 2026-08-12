package taskflow.services.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import taskflow.entity.Task;
import taskflow.entity.dto.task.CreateTaskRequest;
import taskflow.entity.dto.task.TaskResponse;

import java.util.List;

public interface TaskService extends JpaRepository<Task, Long> {
     TaskResponse createTask(Long projectId, CreateTaskRequest request);
     List<TaskResponse> getTasksByProject(Long projectId);
     TaskResponse updateTask(Long projectId, CreateTaskRequest request);
     void delete(Long taskId);
}
