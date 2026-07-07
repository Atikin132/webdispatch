import { computed, inject, Injectable, signal } from '@angular/core';
import { User } from '../models/user';
import { UserService } from './user.service';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private userService = inject(UserService);

  private currentUserId = signal<number | null>(null);

  currentUser = computed<User | null>(() => {
    const id = this.currentUserId();
    return id ? (this.userService.getUser(id) ?? null) : null;
  });

  constructor() {
    const savedId = localStorage.getItem('currentUserId');
    if (savedId) {
      this.currentUserId.set(+savedId);
    }
  }

  login(login: string, password: string): boolean {
    const user = this.userService.getUserByLoginAndPassword(login, password);
    if (!user) {
      return false;
    }

    this.currentUserId.set(user.id);
    localStorage.setItem('currentUserId', user.id.toString());
    return true;
  }

  logout(): void {
    this.currentUserId.set(null);
    localStorage.removeItem('currentUserId');
  }

  isAuthenticated(): boolean {
    return this.currentUserId() !== null;
  }

  changePassword(oldPassword: string, newPassword: string): void {
    const user = this.currentUser();
    if (!user) {
      return;
    }
    this.userService.updatePassword(user.id, oldPassword, newPassword).subscribe();
  }

  hasRole(roleName: string): boolean {
    const user = this.currentUser();
    return user?.roles.some((role) => role.name === roleName) ?? false;
  }
}
