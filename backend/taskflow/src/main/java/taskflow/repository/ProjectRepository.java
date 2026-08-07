package taskflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import taskflow.entity.Project;
import taskflow.entity.User;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByOwner(User owner);
}
