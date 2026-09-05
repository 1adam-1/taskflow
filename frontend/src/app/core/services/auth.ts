import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LoginRequest, AuthenticationResponse, RegisterRequest } from '../models/auth.models';
import { Observable } from 'rxjs/internal/Observable';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private readonly tokenkey = 'accessToken';

  constructor(private http: HttpClient){  }

  login( request: LoginRequest): Observable<AuthenticationResponse> {
    return this.http.post<AuthenticationResponse>(`${environment.apiUrl}/auth/login`, request);
  }

  register( request: RegisterRequest): Observable<AuthenticationResponse>{
    return this.http.post<AuthenticationResponse>(`${environment.apiUrl}/auth/register`, request);
  }

  saveToken(token: string): void{
    localStorage.setItem(this.tokenkey, token);
  }

  getToken():string | null{
    return localStorage.getItem(this.tokenkey);
  }

  logout():void {
    localStorage.removeItem(this.tokenkey);
  }

  isAuthenticated(): boolean {
    return !!this.getToken();
  }

}
