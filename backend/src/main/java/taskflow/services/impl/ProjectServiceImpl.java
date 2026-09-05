package taskflow.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import taskflow.entity.Project;
import taskflow.entity.ProjectMember;
import taskflow.entity.User;
import taskflow.entity.dto.project.CreateProjectRequest;
import taskflow.entity.dto.project.ProjectResponse;
import taskflow.entity.enums.ProjectRole;
import taskflow.entity.enums.ProjectStatus;
import taskflow.exception.UserNotFoundException;
import taskflow.mapper.ProjectMapper;
import taskflow.repository.ProjectMemberRepository;
import taskflow.repository.ProjectRepository;
import taskflow.repository.UserRepository;
import taskflow.services.interfaces.ProjectService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ProjectMapper projectMapper;
    private final ProjectMemberRepository memberRepository;

    public User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email).orElseThrow(()->new UserNotFoundException(email));
    }

    @Override
    public ProjectResponse create(CreateProjectRequest request){
        User owner = getCurrentUser();

        //Create Project

        Project project = Project.builder()
                .name(request.getName())
                .description(request.getDescription())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .status(ProjectStatus.PLANNING)
                .owner(owner).build();

        Project saved = projectRepository.save(project);

        //Create the Owner member
        ProjectMember ownerMember = ProjectMember.builder()
                .project(project)
                .user(owner)
                .role(ProjectRole.OWNER)
                .build();

        memberRepository.save(ownerMember);

        return projectMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    @Override
    public ProjectResponse getById(Long id){
        User currentUser = getCurrentUser();
        Project project = projectRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found: " + id));

        if (!memberRepository.existsByProjectAndUser(project, currentUser)){
            throw new AccessDeniedException("Access denied");
        }
        return projectMapper.toResponse(project);
    }

    @Override
    public List<ProjectResponse> getMyProjects(){
        User currentUser = getCurrentUser();
        List<Project> projects = projectRepository.findDistinctByMembers_User(currentUser);
        return projects.stream().map(projectMapper::toResponse).toList();
    }

    @Override
    public ProjectResponse update(Long id, CreateProjectRequest request){
        User currentUser = getCurrentUser();
        Project project = projectRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found: " +id));
        if(!currentUser.getId().equals(project.getOwner().getId())){
            throw new AccessDeniedException("Access denied");
        }
        project.setName(request.getName());
        project.setDescription(request.getDescription());
        project.setStartDate(request.getStartDate());
        project.setEndDate(request.getEndDate());
        project.setStatus(request.getStatus());

        Project saved = projectRepository.save(project);

        return projectMapper.toResponse(saved);
    }

    @Override
    public void delete(Long id){
        User currentUser = getCurrentUser();
        Project project = projectRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found: " +id));
        if(!currentUser.getId().equals(project.getOwner().getId())){
            throw new AccessDeniedException("Access denied");
        }
        projectRepository.delete(project);
    }
}