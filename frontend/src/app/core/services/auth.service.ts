import { inject, Injectable, signal } from '@angular/core';
import { User } from '../models/user';
import { UserService } from './user.service';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private userService = inject(UserService);
  private currentUser = signal<User | null>(null);

  currentUserSignal = this.currentUser;

  constructor() {
    const user = localStorage.getItem('currentUser');

    if (user) {
      this.currentUser.set(JSON.parse(user));
    }
  }

  login(login: string, password: string): boolean {
    const user = this.userService.getUserByLoginAndPassword(login, password);
    if (!user) {
      return false;
    }

    this.currentUser.set(user);
    localStorage.setItem('currentUser', JSON.stringify(user));
    return true;
  }

  logout(): void {
    this.currentUser.set(null);
    localStorage.removeItem('currentUser');
  }

  isAuthenticated(): boolean {
    return this.currentUser() !== null;
  }

  changePassword(oldPassword: string, newPassword: string): boolean {
    const user = this.currentUser();

    if (!user) {
      return false;
    }

    if (user.password !== oldPassword) {
      return false;
    }

    const successChange = this.userService.updatePassword(user.id, newPassword);

    if (successChange) {
      const updatedUser = { ...user, password: newPassword };
      this.currentUser.set(updatedUser);
      localStorage.setItem('currentUser', JSON.stringify(updatedUser));
      return true;
    }

    return false;
  }

  hasRole(roleName: string): boolean {
    const user = this.currentUser();
    if (!user) {
      return false;
    }
    return user.roles.some((role) => role.name === roleName);
  }
}
