import { Component, inject, signal } from '@angular/core';
import { ProjectsService } from '../../../core/services/projects/projects';
import { Project } from '../../../core/models/projects.models';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { ProjectMember } from '../../../core/models/projectMember.models';
import { MembersService } from '../../../core/services/members/members';
import { TasksList } from '../../tasks/tasks-list/tasks-list';

@Component({
  selector: 'app-project-details',
  imports: [
    RouterLink,
    TasksList
  ],
  templateUrl: './project-details.html',
  styleUrl: './project-details.css',
})
export class ProjectDetails {
  private projectService = inject(ProjectsService);
  private projectMembrService = inject(MembersService);
  private route = inject(ActivatedRoute);

  projectId: number | null = null;
  project: Project | null = null;
  members: ProjectMember[] = [];

  errorMsg = signal('');
  successMsg = signal('');
  loading = signal(false);
  membersLoading = signal(false);
  membersLoaded = signal(false);

  ngOnInit(): void{
    this.loading.set(true);
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if(!id){
      this.errorMsg.set('Invalid project ID');
      return;
    }
    this.projectId = id;

    this.projectService.getProjectById(this.projectId!).subscribe({
      next: (projet ) => {
        this.project = projet;
        this.loading.set(false);
      },
      error: (error) => {
        console.error('Error fetching project:', error);
        this.errorMsg.set(error?.error?.message || 'An error occurred while fetching the project details.');
        this.loading.set(false);
      }
    })

    this.membersLoading.set(true);
    this.projectMembrService.getMembers(this.projectId!).subscribe({
      next: (members) => {
        this.members = members;
        this.membersLoaded.set(true);
        this.membersLoading.set(false);
      },
      error: (error) => {
        console.error('Error fetching project members:', error);
        this.errorMsg.set(error?.error?.message || 'An error occurred while fetching the project members.');
        this.membersLoading.set(false);
      }
    })
  }

  updateMember(id: number): void{
    
  }
  removeMember(memberId: number): void {
    if (!this.projectId) {
      this.errorMsg.set('Invalid project ID');
      return;
    }

    this.projectMembrService.removeMember(this.projectId, memberId).subscribe({
      next: () => {
        this.members = this.members.filter(member => member.id !== memberId);
        this.successMsg.set('Member removed successfully.');
      },
      error: (error) => {
        console.error('Error removing member:', error);
        this.errorMsg.set(error?.error?.message || 'An error occurred while removing the member.');
      }
    });
  }


}
