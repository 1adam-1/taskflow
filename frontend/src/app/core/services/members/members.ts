import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AddMemberRequest, ProjectRole } from '../../models/projectMember.models';
import { ProjectMember } from '../../models/projectMember.models';
import { Observable } from 'rxjs/internal/Observable';
import { environment } from '../../../../environments/environment.development';

@Injectable({
  providedIn: 'root',
})
export class MembersService {

  constructor(private http: HttpClient) { }

  addMember(projectId: number,request: AddMemberRequest): Observable<ProjectMember> {
    return this.http.post<ProjectMember>(`${environment.apiUrl}/projects/${projectId}/members`, request);
  }

  getMembers(projectId: number): Observable<ProjectMember[]> {
    return this.http.get<ProjectMember[]>(`${environment.apiUrl}/projects/${projectId}/members`);
  }

  updateRole(projectId: number, memberId: number, role: ProjectRole): Observable<ProjectMember> {
    return this.http.put<ProjectMember>(`${environment.apiUrl}/projects/${projectId}/members/${memberId}`, { role });
  }


  removeMember(projectId: number, memberId: number): Observable<void> {
    return this.http.delete<void>(`${environment.apiUrl}/projects/${projectId}/members/${memberId}`);
  }
}
