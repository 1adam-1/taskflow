import { Component, inject, Input, signal } from '@angular/core';
import { TasksService } from '../../../core/services/tasks/tasks';
import { Task } from '../../../core/models/tasks.models';
import { ActivatedRoute, RouterLink } from '@angular/router';

@Component({
  selector: 'app-tasks-list',
  imports: [
    RouterLink,
  ],
  templateUrl: './tasks-list.html',
  styleUrl: './tasks-list.css',
})
export class TasksList {

  @Input({ required: true })
  projectId!: number;

  private taskService = inject(TasksService);

  tasks: Task[] = [];

  loading  = signal(false);
  error = '';

  ngOnInit(): void {
    if (this.projectId) {
      this.loadTasksByProject();
    } else {
      this.loadTasksByUser();
    }
  }

  loadTasksByUser(): void {
    this.loading.set(true);
    this.taskService.getTasksByUser().subscribe({
      next: tasks => {
        this.tasks = tasks;
        this.loading.set(false);
      },
      error: error => {
        this.error =
          error.error?.message ??
          'Unable to load tasks.';
        this.loading.set(false);
      }
    })
  }

  loadTasksByProject(): void {

    this.loading.set(true);

    this.taskService
      .getTasksByProject(this.projectId)
      .subscribe({
        next: tasks => {
          this.tasks = tasks;
          this.loading.set(false);
        },

        error: error => {
          this.error =
            error.error?.message ??
            'Unable to load tasks.';

          this.loading.set(false);
        }
      });
  }

  deleteTask(taskId: number): void {

    this.taskService.deleteTask(taskId).subscribe({
      next: () => {
        this.tasks =
          this.tasks.filter(task => task.id !== taskId);
          this.loadTasksByProject();
      },

      error: error => {
        this.error =
          error.error?.message ??
          'Unable to delete task.';
      }
    });
  }
}
