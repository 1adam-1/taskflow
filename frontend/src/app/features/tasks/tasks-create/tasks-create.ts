import { Component, inject, signal } from '@angular/core';
import { TasksService } from '../../../core/services/tasks/tasks';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { MembersService } from '../../../core/services/members/members';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { ProjectMember } from '../../../core/models/projectMember.models';
import { CreateTaskRequest, Priority, TaskStatus } from '../../../core/models/tasks.models';

@Component({
  selector: 'app-tasks-create',
  imports: [
    ReactiveFormsModule
  ],
  templateUrl: './tasks-create.html',
  styleUrl: './tasks-create.css',
})
export class TasksCreate {

  private taskService = inject(TasksService);
  private fb = inject(FormBuilder);
  private memberService = inject(MembersService);
  private route = inject(ActivatedRoute);

  projectId: number|null = null;
  taskId: number|null = null;
  msg = signal('');

  members: ProjectMember[] = [];
  isLoading = signal(false);
  isEditMode = signal(false);

  priorities = Object.values(Priority);
  statuses = Object.values(TaskStatus);

  taskForm = this.fb.nonNullable.group({
    title: ['', [Validators.required, Validators.maxLength(100)]],
    description: ['', [Validators.required]],
    priority: [Priority.MEDIUM, [Validators.required]],
    status: [TaskStatus.TODO, [Validators.required]],
    dueDate: [''],
    assigneeId: [null as number | null]
  })

  ngOnInit(): void{
    this.projectId = Number(this.route.snapshot.paramMap.get('projectId'));
    this.taskId = Number(this.route.snapshot.paramMap.get('taskId'));

    if(this.projectId){
      this.loadMembers(this.projectId);
    }

    if(this.taskId){
      this.isEditMode.set(true);
      this.loadTask(this.taskId);
    }

    console.log('Project ID:', this.projectId);
  }

  loadMembers(projectId: number): void{
    if(this.projectId){
      this.memberService.getMembers(this.projectId).subscribe({
        next: (members) => {
          this.members = members;
        },
        error: (err) => {
          this.msg.set('Failed to load members: ' + err.message);
        }
      });
    }
  }

  loadTask(taskId: number): void{
    this.isLoading.set(true);
    if(this.taskId){
      this.taskService.getTaskById(taskId).subscribe({
        next: (task) => {
          this.taskForm.patchValue(
            {
              title: task.title,
              description: task.description,
              status: task.status,
              priority: task.priority,
              dueDate: task.dueDate!,
              assigneeId: task.assigneeId
            }
          );
          this.isLoading.set(false);
        },
        error: (err) => {
          this.msg.set('Failed to load task: ' + err.message);
          this.isLoading.set(false);
        }
      });
    }
  }

  onSubmit(): void{
    if(this.taskForm.invalid){
      this.msg.set('Please fill in all required fields.');
      return;
    
    }
    const value = this.taskForm.getRawValue();
     const request: CreateTaskRequest = {
        title: value.title!,
        description: value.description!,
        status: value.status!,
        priority: value.priority!,
        dueDate: value.dueDate || undefined,
        assigneeId: value.assigneeId ?? undefined
      };
    if(this.isEditMode()){
      if(this.taskId){
        this.taskService.updateTask(this.taskId, request).subscribe({
          next: (task) => {
            this.msg.set('Task updated successfully.');
          },
          error: (err) => {
            this.msg.set('Failed to update task: ' + err.message);
          }
        })
      }
    }else{
      if(this.projectId){
        this.taskService.createTask(request, this.projectId).subscribe({
          next: (task) => {
            this.msg.set('Task created successfully.');
            this.taskForm.reset({
              title: '',
              description: '',
              priority: Priority.MEDIUM,
              status: TaskStatus.TODO,
              dueDate: '',
              assigneeId: null
            });
          },
          error: (err) => {
            this.msg.set('Failed to create task: ' + err.message);
          }
        })
      }
    }
  }
}
