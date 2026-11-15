import { Component, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { AssistantService } from '../../../core/services/assistant/assistant';

@Component({
  selector: 'app-assistant-component',
  imports: [FormsModule],
  templateUrl: './assistant-component.html',
  styleUrl: './assistant-component.css',
})
export class AssistantComponent {
  private readonly assistantService = inject(AssistantService);

  message = '';
  answer = signal('');
  error = signal('');
  isLoading = signal(false);

  sendMessage(): void {
    const message = this.message.trim();
    if (!message || this.isLoading()) {
      return;
    }

    this.error.set('');
    this.answer.set('');
    this.isLoading.set(true);

    this.assistantService.chat({ message }).subscribe({
      next: (response) => {
        this.answer.set(response.answer);
        this.isLoading.set(false);
      },
      error: (error: HttpErrorResponse) => {
        this.error.set(
          error.error?.detail ?? 'Unable to contact the assistant.',
        );
        this.isLoading.set(false);
      },
    });
  }
}
