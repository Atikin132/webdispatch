import { Injectable, signal } from '@angular/core';
import { User } from '../models/user';
import { USERS } from '../mock/users.mock';

@Injectable({ providedIn: 'root' })
export class UserService {
  private users = signal<User[]>([...USERS]);

  getUsers() {
    return this.users.asReadonly();
  }

  getUser(id: number): User | undefined {
    return this.users().find((user) => user.id === id);
  }

  create(user: User) {
    const newId =
      this.users().length > 0 ? Math.max(...this.users().map((user) => user.id)) + 1 : 1;

    this.users.update((users) => [
      ...users,
      {
        ...user,
        id: newId,
      },
    ]);
  }

  update(updatedUser: User) {
    this.users.update((users) =>
      users.map((user) => (user.id === updatedUser.id ? updatedUser : user)),
    );
  }

  delete(id: number) {
    this.users.update((users) => users.filter((user) => user.id !== id));
  }

  loginExists(login: string, currentId?: number): boolean {
    return this.users().some(
      (user) => user.login.toLowerCase() === login.toLowerCase() && user.id !== currentId,
    );
  }

  validateUser(user: User): string | null {
    if (user.birthDate && new Date(user.birthDate) >= new Date()) {
      return 'Birth date must be in the past';
    }

    if (user.login && this.loginExists(user.login, user.id)) {
      return 'Login already exists';
    }
    return null;
  }

  getUserByLoginAndPassword(login: string, password: string): User | undefined {
    return USERS.find((user) => user.login === login && user.password === password);
  }

  updatePassword(userId: number, newPassword: string): boolean {
    const index = USERS.findIndex((user) => user.id === userId);
    if (index === -1) {
      return false;
    }

    USERS[index] = {
      ...USERS[index],
      password: newPassword,
    };
    return true;
  }
}
