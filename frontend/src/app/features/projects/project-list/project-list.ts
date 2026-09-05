import { Component, inject, signal } from '@angular/core';
import { ProjectsService } from '../../../core/services/projects/projects';
import { Project } from '../../../core/models/projects.models';
import { finalize } from 'rxjs';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-project-list',
  imports: [RouterLink],
  templateUrl: './project-list.html',
  styleUrl: './project-list.css',
})
export class ProjectList {

  private projectService = inject(ProjectsService);

  projectsList: Project[] = [];
  loading = signal(false);
  errorMsg = signal('');
  successMsg = signal('');

  ngOnInit(): void {
    this.loadProjects();
  }

loadProjects(): void {
    this.loading.set(true);
    this.errorMsg.set('');
    this.projectService.getProjects().pipe(
      finalize(() => {
        this.loading.set(false);
      })
    ).subscribe({
      next: (projects) => {
        this.projectsList = projects ?? [];
      },
      error: (error) => {
        this.errorMsg.set('Failed to load projects.' + error.message);
      }
    });
  }

  deleteProject(id: number):void {
    this.projectService.deleteProject(id).subscribe({
      next: ()=> {
        this.projectsList= this.projectsList.filter(project => project.id !== id);
        this.successMsg.set('Project deleted successfully');
        this.loadProjects();
      },
      error: (error) => {
        if (error.status === 403) {
          this.errorMsg.set('Failed to delete project. You do not have permission.');
        } else {
          this.errorMsg.set('Failed to delete project.' + error.message);
        }
      }
    })
  }
}
