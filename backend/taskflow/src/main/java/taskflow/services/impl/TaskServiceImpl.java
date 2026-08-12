package taskflow.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import taskflow.entity.Project;
import taskflow.entity.Task;
import taskflow.entity.User;
import taskflow.entity.dto.task.CreateTaskRequest;
import taskflow.entity.dto.task.TaskResponse;
import taskflow.mapper.TaskMapper;
import taskflow.repository.ProjectRepository;
import taskflow.repository.TaskRepository;
import taskflow.repository.UserRepository;
import taskflow.services.interfaces.TaskService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;

    @Override
    public TaskResponse createTask(Long projectId, CreateTaskRequest request){
        Project project = projectRepository.findById(projectId).orElseThrow(()-> new RuntimeException("Project not found"));
        User assignee = userRepository.findById(request.getAssigneeId()).orElseThrow(()-> new RuntimeException("User not found"));

        Task task  = Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .status(request.getStatus())
                .priority(request.getPriority())
                .dueDate(request.getDueDate())
                .project(project)
                .assignee(assignee)
                .build();

        return taskMapper.toResponse(task);
    }

    @Override
    public List<TaskResponse> getTasksByProject(Long projectId){
        Project project = projectRepository.findById(projectId).orElseThrow(()-> new RuntimeException("Project not found"));
        List<Task> tasks = taskRepository.findByProject(project);

        return tasks.stream().map(taskMapper::toResponse).toList();
    }

    @Override
    public TaskResponse updateTask(Long projectId, CreateTaskRequest request){
        Project project = projectRepository.findById(projectId).orElseThrow(()-> new RuntimeException("Project not found"));
        User assignee = userRepository.findById(request.getAssigneeId()).orElseThrow(()-> new RuntimeException("User not found"));
        if (project.)
    }
}
