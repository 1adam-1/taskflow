package taskflow.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import taskflow.entity.audit.BaseEntity;
import taskflow.entity.enums.ProjectStatus;

import java.time.LocalDate;

@Entity
@Table(name="projects")
@Data
@Builder
public class Project extends BaseEntity {

    @NotBlank
    @Size(max=100)
    @Column(nullable = false)
    private String name;

    private String description;

    @NotNull
    @Column(nullable = false)
    private LocalDate startDate;

    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @NotNull
    private ProjectStatus status;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;


}
