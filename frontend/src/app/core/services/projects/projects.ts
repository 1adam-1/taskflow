import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CreateProjectRequest, Project } from '../../models/projects.models';
import { Observable } from 'rxjs/internal/Observable';
import { environment } from '../../../../environments/environment.development';

@Injectable({
  providedIn: 'root',
})
export class ProjectsService {

  constructor(private http: HttpClient){}

  createProject(request: CreateProjectRequest): Observable<Project> {
    return this.http.post<Project>(`${environment.apiUrl}/projects`, request);
  }

  getProjectById(id: number): Observable<Project> {
    return this.http.get<Project>(`${environment.apiUrl}/projects/${id}`)
  }

  getProjects(): Observable<Project[]> {
    return this.http.get<Project[]>(`${environment.apiUrl}/projects`);
  }

  updateProject(id: number, request: CreateProjectRequest): Observable<Project> {
    return this.http.put<Project>(`${environment.apiUrl}/projects/${id}`, request);
  }

  deleteProject(id: number): Observable<void> {
    return this.http.delete<void>(`${environment.apiUrl}/projects/${id}`);
  }
}
