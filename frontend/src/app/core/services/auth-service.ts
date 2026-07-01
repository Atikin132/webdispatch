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
}
