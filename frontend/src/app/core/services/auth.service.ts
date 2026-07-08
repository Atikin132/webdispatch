import { inject, Injectable } from '@angular/core';
import { environment } from '../environment';
import { JWTResponse } from '../models/jwt-response';
import { HttpClient } from '@angular/common/http';
import { jwtDecode } from 'jwt-decode';

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
    const token = localStorage.getItem('token');
    if (!token) {
      return false;
    }
    try {
      const decoded = jwtDecode<{ exp?: number }>(token);
      const isTokenNotExpired = decoded.exp !== undefined && decoded.exp * 1000 > Date.now();
      if (!isTokenNotExpired) {
        this.logout();
      }
      return isTokenNotExpired;
    } catch {
      return false;
    }
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
