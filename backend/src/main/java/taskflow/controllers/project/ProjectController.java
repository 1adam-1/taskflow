package taskflow.controllers.project;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import taskflow.entity.dto.project.CreateProjectRequest;
import taskflow.entity.dto.project.ProjectResponse;
import taskflow.services.interfaces.ProjectService;

import java.util.List;

@RestController
@RequestMapping("api/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<ProjectResponse>  createProject(@Valid @RequestBody CreateProjectRequest request){
        ProjectResponse response = projectService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponse> getProjectById(@PathVariable Long id){
        ProjectResponse response = projectService.getById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ProjectResponse>> getProjectsByOwner(){
        List<ProjectResponse> projects = projectService.getMyProjects();
        return ResponseEntity.ok(projects);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectResponse> updateProject(@PathVariable Long id, @Valid @RequestBody CreateProjectRequest request){
        ProjectResponse response = projectService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>  deleteProject(@PathVariable Long id){
        projectService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
