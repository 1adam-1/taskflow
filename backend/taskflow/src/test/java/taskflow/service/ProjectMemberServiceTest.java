package taskflow.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import taskflow.entity.Project;
import taskflow.entity.ProjectMember;
import taskflow.entity.User;
import taskflow.entity.dto.projectmember.AddMemberRequest;
import taskflow.entity.dto.projectmember.ProjectMemberResponse;
import taskflow.entity.enums.ProjectRole;
import taskflow.mapper.ProjectMemberMapper;
import taskflow.repository.ProjectMemberRepository;
import taskflow.repository.ProjectRepository;
import taskflow.repository.UserRepository;
import taskflow.services.impl.ProjectMemberServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProjectMemberServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private ProjectMemberRepository memberRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @Mock
    private ProjectMemberMapper projectMemberMapper;

    @InjectMocks
    private ProjectMemberServiceImpl projectMemberService;

    @BeforeEach
    void setUp(){
        SecurityContextHolder.setContext(securityContext);
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void should_add_member(){
    Long projectId = 11L;
    User currentUser  = new User();
    User userToAdd = new User();

    Project project = new Project();

    ProjectMember currentMember = new ProjectMember();
    currentMember.setRole(ProjectRole.OWNER);

    AddMemberRequest request = new AddMemberRequest(
                        "test@test.com",
                        ProjectRole.MANAGER
                );

    ProjectMemberResponse expectedResponse = new ProjectMemberResponse(
                userToAdd.getId(),userToAdd.getFirstName(),userToAdd.getLastName(), userToAdd.getEmail(), ProjectRole.MANAGER
        );

    when(securityContext.getAuthentication()).thenReturn(authentication);
    when(authentication.getName()).thenReturn("test@test.com");
    when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(currentUser));
    when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
    when(memberRepository.findByProjectAndUser(project, currentUser)).thenReturn(Optional.of(currentMember));
    when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(userToAdd));
    when(memberRepository.existsByProjectAndUser(project, userToAdd)).thenReturn(false);
    when(memberRepository.save(any(ProjectMember.class))).thenAnswer(invocation -> invocation.getArgument(0));
    when(projectMemberMapper.toResponse(any(ProjectMember.class))).thenReturn(expectedResponse);


    ProjectMemberResponse response = projectMemberService.addMember(projectId, request);

    assertNotNull(response);

        ArgumentCaptor<ProjectMember> captor =
                ArgumentCaptor.forClass(ProjectMember.class);

        verify(memberRepository).save(captor.capture());

        ProjectMember savedMember = captor.getValue();

        assertEquals(project, savedMember.getProject());
        assertEquals(userToAdd, savedMember.getUser());
        assertEquals(ProjectRole.MANAGER, savedMember.getRole());

    }
}
