import { Component, inject, signal } from '@angular/core';
import { ProjectsService } from '../../../core/services/projects/projects';
import { Router } from '@angular/router';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';

@Component({
  selector: 'app-project-create',
  imports: [ReactiveFormsModule],
  templateUrl: './project-create.html',
  styleUrl: './project-create.css',
})
export class ProjectCreate {

  private projectService = inject(ProjectsService);
  private router = inject(Router);
  private fb = inject(FormBuilder);

  successMsg=signal('');
  errorMsg=signal('');

  CreateProjectForm = this.fb.nonNullable.group({
    name: ['', Validators.required],
    description: ['', Validators.required],
    startDate: ['', Validators.required],
    endDate: [''],
  })

  

  onSubmit(): void{
    this.errorMsg.set('');
    this.successMsg.set('');

    const {name, description, startDate, endDate} = this.CreateProjectForm.getRawValue();
    const req = {name, description, startDate, endDate};

    if(this.CreateProjectForm.invalid) {
      this.CreateProjectForm.markAllAsTouched();
      return;
    }

    this.projectService.createProject(req).subscribe({
      next: (response) => {
        this.successMsg.set('Project created successfully!');
          },
      error: (error) => {
        this.errorMsg.set('Something went wrong. Please try again.'  + error);
      }
    })
  }

}
