package taskflow.controllers.projectmember;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import taskflow.entity.dto.projectmember.AddMemberRequest;
import taskflow.entity.dto.projectmember.ProjectMemberResponse;
import taskflow.entity.enums.ProjectRole;
import taskflow.services.interfaces.ProjectMemberService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/projects/{projectId}/members")
public class ProjectMemberController {
    private final ProjectMemberService memberService;

    @PostMapping
    public ResponseEntity<ProjectMemberResponse> addMember(@PathVariable Long projectId, @Valid @RequestBody AddMemberRequest request){
        ProjectMemberResponse member = memberService.addMember(projectId,request);
        return ResponseEntity.status(HttpStatus.CREATED).body(member);
    }

    @GetMapping
    public ResponseEntity<List<ProjectMemberResponse>> getMembers(@PathVariable Long projectId){
        List<ProjectMemberResponse> members = memberService.getMembers(projectId);
        return ResponseEntity.ok(members);
    }

    @PutMapping("/{memberId}")
    public ResponseEntity<Void> updateRolde(@PathVariable Long memberId, @PathVariable Long projectId, ProjectRole role){
        memberService.updateRole(projectId, memberId, role);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long projectId, @PathVariable Long memberId){
        memberService.removeMember(projectId, memberId);
        return ResponseEntity.noContent().build();
    }


}
