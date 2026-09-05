import { Component, inject, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from '../../../core/services/auth';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-register',
  imports: [
     CommonModule,
    ReactiveFormsModule
  ],
  templateUrl: './register.html',
  styleUrl: './register.css',
})
export class Register {
    private fb = inject(FormBuilder);
  private authService = inject(AuthService);
  private router = inject(Router);

  errorMessage = signal('');
  successMessage = signal('');

  registerForm = this.fb.nonNullable.group({
    firstName: ['', Validators.required],
    lastName: ['', Validators.required],
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(6)]]
  })

  onSubmit(): void {
    if (this.registerForm.invalid){
      this.registerForm.markAllAsTouched();
      return;
    }

    const {firstName, lastName, email, password} = this.registerForm.getRawValue();
    const req = {firstName, lastName, email, password};

    this.authService.register(req).subscribe({
      next: (response) => {
        this.successMessage.set('Registration successful! Please log in.');
      },
      error: (error) => {
        if (error.status === 409) {
          this.errorMessage.set('Email already exists');
        } else {
          this.errorMessage.set('Something went wrong. Please try again.');
        }
      }
    })
  }
}
