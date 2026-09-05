package taskflow.entity;

import jakarta.persistence.*;
import lombok.*;
import taskflow.entity.audit.BaseEntity;
import taskflow.entity.enums.ProjectRole;

@Entity
@Table(name = "project_members",
        uniqueConstraints = {
        @UniqueConstraint(columnNames = {"project_id", "user_id"})
        }
)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectMember extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProjectRole role;
}
