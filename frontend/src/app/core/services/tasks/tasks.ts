import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CreateTaskRequest, Task } from '../../models/tasks.models';
import { environment } from '../../../../environments/environment.development';
import { Observable } from 'rxjs/internal/Observable';

@Injectable({
  providedIn: 'root',
})
export class TasksService {

  constructor(private http: HttpClient) {}

  createTask(request: CreateTaskRequest, projectId: number): Observable<Task> {
    return this.http.post<Task>(`${environment.apiUrl}/projects/${projectId}/tasks`, request);
  }

  getTasksByProject(projectId: number): Observable<Task[]> {
    return this.http.get<Task[]>(`${environment.apiUrl}/projects/${projectId}/tasks`);
  }

  getTasksByUser(): Observable<Task[]> {
    return this.http.get<Task[]>(`${environment.apiUrl}/tasks`);
  }
  
  getTaskById(taskId: number): Observable<Task> {
    return this.http.get<Task>(`${environment.apiUrl}/tasks/${taskId}`);
  }

  updateTask(taskId: number, request: CreateTaskRequest): Observable<Task> {
    return this.http.put<Task>(`${environment.apiUrl}/tasks/${taskId}`, request);
  }

  deleteTask(taskId: number): Observable<void> {
    return this.http.delete<void>(`${environment.apiUrl}/tasks/${taskId}`);
  }
}
