package taskflow.services.interfaces;

import taskflow.entity.dto.projectmember.AddMemberRequest;
import taskflow.entity.dto.projectmember.ProjectMemberResponse;
import taskflow.entity.enums.ProjectRole;

import java.util.List;

public interface ProjectMemberService {

    ProjectMemberResponse addMember(Long projectId, AddMemberRequest request);

    List<ProjectMemberResponse> getMembers(Long projectId);

    void removeMember(Long projectId, Long memberId);

    void updateRole(Long projectId, Long memberId, ProjectRole role);
}
