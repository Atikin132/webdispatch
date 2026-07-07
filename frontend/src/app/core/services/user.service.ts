import { computed, inject, Injectable, signal } from '@angular/core';
import { User } from '../models/user';
import { USERS } from '../mock/users.mock';
import { TranslateService } from '@ngx-translate/core';
import { RoleTranslateService } from './role-translate.service';

@Injectable({ providedIn: 'root' })
export class UserService {
  private translate = inject(TranslateService);
  private roleTranslate = inject(RoleTranslateService);

  private users = signal<User[]>([...USERS]);

  readonly usersTranslated = computed(() => {
    const rawUsers = this.users();
    this.roleTranslate.langSignal();
    return this.roleTranslate.translateUsersRoles(rawUsers);
  });

  getUsers() {
    return this.usersTranslated;
  }

  getUser(id: number): User | undefined {
    return this.users().find((user) => user.id === id);
  }

  create(user: User) {
    const users = this.users();
    const newId = users.length === 0 ? 1 : Math.max(...users.map((user) => user.id)) + 1;
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
    const normalizedLogin = login.toLowerCase();
    return this.users().some(
      (user) => user.login.toLowerCase() === normalizedLogin && user.id !== currentId,
    );
  }

  validateUser(user: User): string | null {
    if (user.birthDate && new Date(user.birthDate) >= new Date()) {
      return this.translate.instant('validationBirthDateFuture');
    }

    if (user.login && this.loginExists(user.login, user.id)) {
      return this.translate.instant('userAlreadyExists');
    }
    return null;
  }

  getUserByLoginAndPassword(login: string, password: string): User | undefined {
    return this.users().find((user) => user.login === login && user.password === password);
  }

  updatePassword(userId: number, newPassword: string): boolean {
    const user = this.users().find((user) => user.id === userId);
    if (!user) {
      return false;
    }

    this.users.update((users) =>
      users.map((user) =>
        user.id === userId
          ? {
              ...user,
              password: newPassword,
            }
          : user,
      ),
    );

    return true;
  }
}
