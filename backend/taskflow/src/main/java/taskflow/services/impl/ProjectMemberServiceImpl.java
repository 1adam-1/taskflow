package taskflow.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import taskflow.entity.Project;
import taskflow.entity.ProjectMember;
import taskflow.entity.User;
import taskflow.entity.dto.projectmember.AddMemberRequest;
import taskflow.entity.dto.projectmember.ProjectMemberResponse;
import taskflow.mapper.ProjectMemberMapper;
import taskflow.repository.ProjectMemberRepository;
import taskflow.repository.ProjectRepository;
import taskflow.repository.UserRepository;
import taskflow.services.interfaces.ProjectMemberService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectMemberServiceImpl implements ProjectMemberService {
    private final UserRepository userRepository;
    private final ProjectMemberRepository memberRepository;
    private final ProjectRepository projectRepository;
    private final ProjectMemberMapper projectMemberMapper;


    @Override
    public ProjectMemberResponse addMember(Long projectId, AddMemberRequest request){
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() ->
                        new RuntimeException("Project not found")
                );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(()->new RuntimeException("user with id :" + request.getEmail() + " not found"));


        if(memberRepository.existByProjectAndUser(project,user)){
            throw new RuntimeException("User already exist in the project");
        }

        ProjectMember member = ProjectMember.builder()
                .user(user)
                .project(project)
                .role(request.getRole())
                .build();

        ProjectMember savedMember = memberRepository.save(member);

        return projectMemberMapper.toResponse(savedMember);
    }

    @Override
    public List<ProjectMemberResponse> getMembers(Long projectId){
        Project project = projectRepository.findById(projectId).orElseThrow(()->new RuntimeException("Project not found"));
        List<ProjectMember> members = memberRepository.findByProject(project);
        return members.stream().map(projectMemberMapper::toResponse).toList();
    }

    @Override
    public void removeMember(long projectId, long memberId){
        Project project = projectRepository.findById(projectId).orElseThrow(()-> new RuntimeException("Project not found"));
        ProjectMember member = memberRepository.findById(memberId).orElseThrow(()->new RuntimeException("User not found"));
        if(!member.getProject().getId().equals(projectId)){
            throw new RuntimeException(
                    "Member does not belong to this project"
            );
        }

    }

}
