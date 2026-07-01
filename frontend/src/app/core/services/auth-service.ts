import { Injectable, signal } from '@angular/core';
import { USERS } from '../mock/users.mock';
import { User } from '../models/user';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private currentUser = signal<User | null>(null);

  currentUserSignal = this.currentUser;

  constructor() {
    const user = localStorage.getItem('currentUser');

    if (user) {
      this.currentUser.set(JSON.parse(user));
    }
  }

  login(login: string, password: string): boolean {
    const user = USERS.find((user: User) => user.login === login && user.password === password);

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
    return !!localStorage.getItem('currentUser');
  }

  changePassword(oldPassword: string, newPassword: string): boolean {
    const user = this.currentUser();

    if (!user) {
      return false;
    }

    if (user.password !== oldPassword) {
      return false;
    }

    const updatedUser: User = {
      ...user,
      password: newPassword,
    };

    const index = USERS.findIndex((user) => user.id === updatedUser.id);

    if (index !== -1) {
      USERS[index] = {
        ...updatedUser,
      };
    }

    this.currentUser.set(updatedUser);
    localStorage.setItem('currentUser', JSON.stringify(updatedUser));
    return true;
  }

  hasRole(roleName: string): boolean {
    const user = this.currentUser();
    if (!user) {
      return false;
    }
    return user.roles.some((role) => role.name === roleName);
  }
}
