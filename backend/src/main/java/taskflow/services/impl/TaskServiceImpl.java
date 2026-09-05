package taskflow.services.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import taskflow.entity.Project;
import taskflow.entity.ProjectMember;
import taskflow.entity.Task;
import taskflow.entity.User;
import taskflow.entity.dto.task.CreateTaskRequest;
import taskflow.entity.dto.task.TaskResponse;
import taskflow.exception.UserNotFoundException;
import taskflow.mapper.TaskMapper;
import taskflow.repository.ProjectMemberRepository;
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
    private final ProjectMemberRepository memberRepository;

    public User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email).orElseThrow(()->new UserNotFoundException(email));
    }

    @Override
    public TaskResponse createTask(Long projectId, CreateTaskRequest request){
        Project project = projectRepository.findById(projectId).orElseThrow(()-> new RuntimeException("Project not found"));
        User assignee = null;

        if (request.getAssigneeId() != null) {
            assignee = userRepository.findById(request.getAssigneeId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
        }

        Task task  = Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .status(request.getStatus())
                .priority(request.getPriority())
                .dueDate(request.getDueDate())
                .project(project)
                .assignee(assignee)
                .build();

        Task saved = taskRepository.save(task);

        return taskMapper.toResponse(saved);
    }

    @Transactional
    @Override
    public List<TaskResponse> getTasksByProject(Long projectId){
        Project project = projectRepository.findById(projectId).orElseThrow(()-> new RuntimeException("Project not found"));
        List<Task> tasks = taskRepository.findByProject(project);

        return tasks.stream().map(taskMapper::toResponse).toList();
    }

    @Transactional
    @Override
    public TaskResponse getTaskById(Long taskId){
        Task task = taskRepository.findById(taskId).orElseThrow(()-> new RuntimeException("Task not found"));
        return taskMapper.toResponse(task);
    }

    @Transactional
    @Override
    public List<TaskResponse> getTasksByAssignee(){
        User assignee = getCurrentUser();
        List<Task> tasks = taskRepository.findByAssignee(assignee);
        return tasks.stream().map(taskMapper::toResponse).toList();
    }

    @Override
    public TaskResponse updateTask(Long taskId, CreateTaskRequest request){
        Task task = taskRepository.findById(taskId).orElseThrow(()-> new RuntimeException("task not found"));
        Project project = projectRepository.findById(task.getProject().getId()).orElseThrow(()-> new RuntimeException("Project not found"));
        User assignee = null;

        if (request.getAssigneeId() != null) {
            assignee = userRepository.findById(request.getAssigneeId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
        }        ProjectMember member = memberRepository.findByProjectAndUser(project, assignee).orElseThrow(()-> new RuntimeException("Member not found"));

        if (!member.getProject().getId().equals(project.getId())){
            throw new RuntimeException("Member not found in the project");
        }

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(request.getStatus());
        task.setPriority(request.getPriority());
        task.setDueDate(request.getDueDate());
        task.setAssignee(assignee);

        Task saved = taskRepository.save(task);

        return taskMapper.toResponse(saved);

    }

    @Override
    public void delete(Long taskId){
        Task task = taskRepository.findById(taskId).orElseThrow(()->new RuntimeException("Task not found"));
        taskRepository.delete(task);
    }
}
