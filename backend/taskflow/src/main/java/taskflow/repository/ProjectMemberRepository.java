package taskflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import taskflow.entity.Project;
import taskflow.entity.ProjectMember;
import taskflow.entity.User;

import java.util.List;
import java.util.Optional;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Long> {
    List<ProjectMember> findByUser(User user);
    List<ProjectMember> findByProject(Project project);
    Optional<ProjectMember> findByProjectAndUser(Project project, User user);
    boolean existsByProjectAndUser(Project project, User user);

}
