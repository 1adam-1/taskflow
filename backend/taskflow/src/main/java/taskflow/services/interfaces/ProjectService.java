package taskflow.services.interfaces;

import taskflow.entity.dto.project.CreateProjectRequest;
import taskflow.entity.dto.project.ProjectResponse;

import java.util.List;

public interface ProjectService {
    ProjectResponse create(CreateProjectRequest request);
    List<ProjectResponse> getMyProjects();
    ProjectResponse getById(Long i);
    ProjectResponse update(Long id, CreateProjectRequest request);
    void delete(Long id);
}
