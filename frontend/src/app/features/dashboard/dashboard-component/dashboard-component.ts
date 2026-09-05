import { Component, inject, signal } from '@angular/core';
import { ProjectsService } from '../../../core/services/projects/projects';
import { TasksService } from '../../../core/services/tasks/tasks';
import { Project } from '../../../core/models/projects.models';
import { Task, TaskStatus } from '../../../core/models/tasks.models';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-dashboard-component',
  imports: [
    RouterLink,
  ],
  templateUrl: './dashboard-component.html',
  styleUrl: './dashboard-component.css',
})
export class DashboardComponent {

  private projectService = inject(ProjectsService);
  private taskService = inject(TasksService);
  
  projects: Project[] = [];
  tasks:Task[] = [];

  msg = signal('');

  isLoading = signal(false);
  isProjectsLoaded = signal(false);
  isTasksLoaded = signal(false);

  ngOnInit(): void {
    this.loadProjects();
  }

  loadProjects(): void {
    this.isLoading.set(true);

    this.projectService.getProjects().subscribe({
      next: (projects) => {
        this.projects = projects;
        if (projects.length === 0) {
          this.msg.set('No projects found');
          return;
        }
        for (const project of projects) {
          this.taskService.getTasksByProject(project.id).subscribe({
            next: (tasks) => {
              this.tasks.push(...tasks);
            },
            error: (error) => {
              this.msg.set('Error loading tasks for project ' + project.name);
            },
            complete: () => {
              this.isTasksLoaded.set(true);
            }
          })
        }
        this.isProjectsLoaded.set(true);
        this.isLoading.set(false);
      },
      error: (error) => {
        this.msg.set('Error loading projects');
        this.isLoading.set(false);
        this.isProjectsLoaded.set(false);
      }
    })
  }

  get inpprogressCount(): number { 
    return this.tasks.filter(task => task.status === TaskStatus.IN_PROGRESS).length;
  }

   get inProgressCount(): number {
    return this.tasks.filter(
      task => task.status === TaskStatus.IN_PROGRESS
    ).length;
  }

  get completedCount(): number {
    return this.tasks.filter(
      task => task.status === TaskStatus.DONE
    ).length;
  }

  get recentProjects(): Project[] {
    return this.projects.slice(0, 5);
  }

}
