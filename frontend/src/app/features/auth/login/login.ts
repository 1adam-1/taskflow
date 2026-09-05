import { Component, inject, signal } from '@angular/core';
import { AuthService } from '../../../core/services/auth';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { LoginRequest } from '../../../core/models/auth.models';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-login',
  imports: [
     CommonModule,
    ReactiveFormsModule
  ],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {

  private authService = inject(AuthService);
  private fb = inject(FormBuilder);
  private router = inject(Router);

  loginForm = this.fb.nonNullable.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(6)]]
  });

  errorMessage = signal('');

  onSubmit() :void{
    if (this.loginForm.invalid) {
      this.loginForm.markAllAsTouched();
      return;
    }

    this.errorMessage.set('');

    const {email, password} = this.loginForm.getRawValue();
    const req : LoginRequest = {email, password};

    this.authService.login(req).subscribe({
      next: (response) => {
        this.authService.saveToken(response.accessToken);
        this.router.navigate(['/projects']);
      },
      error: (error) => {
        if (error.status === 401 || error.status === 403) {
    this.errorMessage.set('Invalid email or password');
  } else {
    this.errorMessage.set('Something went wrong. Please try again.');
  }
      }
    })
  }
  
  
}
