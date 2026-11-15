import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment';
import { ChatRequest, ChatResponse } from '../../models/assistant.models';

@Injectable({
  providedIn: 'root',
})
export class AssistantService {
  private readonly assistantUrl = `${environment.apiUrl}/assistant/chat`;

  constructor(private readonly http: HttpClient) {}

  chat(request: ChatRequest): Observable<ChatResponse> {
    return this.http.post<ChatResponse>(this.assistantUrl, request);
  }
}
