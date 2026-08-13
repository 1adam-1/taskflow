package taskflow.controllers.task;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import taskflow.entity.dto.task.CreateTaskRequest;
import taskflow.entity.dto.task.TaskResponse;
import taskflow.services.interfaces.TaskService;

import java.util.List;

@RestController
@RequestMapping("api/projects/{projectId}/tasks")
@RequiredArgsConstructor
public class ProjectTaskController {
    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@PathVariable Long projectId, @Valid @RequestBody CreateTaskRequest request){
        TaskResponse task = taskService.createTask(projectId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(task);
    }

    @GetMapping
    public ResponseEntity<List<TaskResponse>> getTasksByProject(@PathVariable Long projectId){
        List<TaskResponse> tasks = taskService.getTasksByProject(projectId);
        return ResponseEntity.ok(tasks);
    }
}
