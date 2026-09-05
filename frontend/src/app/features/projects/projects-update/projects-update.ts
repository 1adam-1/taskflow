import { Component, inject, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ProjectsService } from '../../../core/services/projects/projects';
import { Project } from '../../../core/models/projects.models';

@Component({
  selector: 'app-projects-update',
  imports: [ReactiveFormsModule],
  templateUrl: './projects-update.html',
  styleUrl: './projects-update.css',
})
export class ProjectsUpdate {
private projectService = inject(ProjectsService);
private fb = inject(FormBuilder)
private router = inject(Router);
private route = inject(ActivatedRoute);

private projectId: number | null = null;
project: Project | null = null;
successMsg = signal('');
errorMsg = signal('');

UpdateProjectForm = this.fb.nonNullable.group({
  name: [''],
  description: [''],
  startDate: [''],
  endDate: [''],
  status: [''], 
});

ngOnInit(): void{
  this.projectId= Number(this.route.snapshot.paramMap.get('id'));
  if(this.projectId){
    this.projectService.getProjectById(this.projectId).subscribe({
      next: (project) => {
        this.project = project;
        this.UpdateProjectForm.patchValue({
          name: project.name,
          description: project.description,
          startDate: project.startDate,
          endDate: project.endDate,
          status: project.status,
        });
      },
      error: (error) => {
        this.errorMsg.set(error?.error?.message || 'An error occurred while fetching the project details.');
      }
    })
  }

}

updateProject(): void{
  this.successMsg.set('');
  this.errorMsg.set('');
  const request = this.UpdateProjectForm.getRawValue();
  if (request.startDate > request.endDate) {
    this.errorMsg.set('Start date cannot be after end date');
    return; 
  }

  this.projectService.updateProject(this.projectId!, request).subscribe({
    next: (updatedProject) => {
      this.successMsg.set('Project updated successfully');
    },
    error: (error) => {
      if(error.status ===403){
        this.errorMsg.set('You are not authorized to update this project.');
      }
    }
  });
}
}
