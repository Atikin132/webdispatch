import { inject, Injectable } from '@angular/core';
import { environment } from '../environment';
import { JWTResponse } from '../models/jwt-response';
import { HttpClient } from '@angular/common/http';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private http = inject(HttpClient);
  private readonly apiUrl = environment.apiUrl + '/login';

  login(login: string, password: string) {
    return this.http.post<JWTResponse>(`${this.apiUrl}`, {
      login,
      password,
    });
  }

  logout(): void {
    localStorage.removeItem('token');
    localStorage.removeItem('currentUserId');
    localStorage.removeItem('login');
    localStorage.removeItem('roles');
  }

  isAuthenticated(): boolean {
    return !!localStorage.getItem('token');
  }

  hasRole(role: string): boolean {
    const roles = JSON.parse(localStorage.getItem('roles') ?? '[]');
    return roles.includes(role);
  }

  getCurrentUserId(): number | null {
    const id = localStorage.getItem('currentUserId');
    return id ? Number(id) : null;
  }

  getCurrentLogin(): string | null {
    return localStorage.getItem('login');
  }
}
