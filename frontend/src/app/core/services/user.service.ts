import { computed, inject, Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { TranslateService } from '@ngx-translate/core';
import { catchError, EMPTY, map, switchMap, tap } from 'rxjs';

import { User } from '../models/user';
import { RoleTranslateService } from './role-translate.service';
import { environment } from '../environment';

@Injectable({ providedIn: 'root' })
export class UserService {
  private http = inject(HttpClient);
  private translate = inject(TranslateService);
  private roleTranslate = inject(RoleTranslateService);

  private readonly apiUrl = environment.apiUrl + '/users';

  private readonly users = signal<User[]>([]);

  readonly usersTranslated = computed(() => {
    this.roleTranslate.langSignal();
    return this.roleTranslate.translateUsersRoles(this.users());
  });

  loadUsers() {
    return this.http.get<User[]>(this.apiUrl).pipe(
      tap((users) => this.users.set(users)),
      catchError(() => {
        return EMPTY;
      }),
    );
  }

  getUsers() {
    return this.usersTranslated;
  }

  getUser(id: number): User | undefined {
    return this.users().find((user) => user.id === id);
  }

  create(user: User) {
    return this.http.post<User>(this.apiUrl, this.toUserFormDTO(user)).pipe(
      switchMap(() => this.loadUsers()),
      map(() => void 0),
    );
  }

  update(user: User) {
    return this.http.put<User>(`${this.apiUrl}/${user.id}`, this.toUserFormDTO(user)).pipe(
      switchMap(() => this.loadUsers()),
      map(() => void 0),
    );
  }

  delete(id: number) {
    return this.http.delete<void>(`${this.apiUrl}/${id}`).pipe(
      switchMap(() => this.loadUsers()),
      map(() => void 0),
    );
  }

  updatePassword(userId: number, oldPassword: string, newPassword: string) {
    return this.http.put<{ message: string }>(
      `${environment.apiUrl}/password/${userId}`,
      this.toPasswordChangeFormDTO(oldPassword, newPassword),
    );
  }

  loginExists(login: string, currentId?: number): boolean {
    return this.users().some(
      (user) => user.login.toLowerCase() === login.toLowerCase() && user.id !== currentId,
    );
  }

  validateUser(user: User): string | null {
    if (user.birthDate && new Date(user.birthDate) >= new Date()) {
      return this.translate.instant('validationBirthDateFuture');
    }

    if (this.loginExists(user.login, user.id)) {
      return this.translate.instant('userAlreadyExists');
    }
    return null;
  }

  private toUserFormDTO(user: User) {
    return {
      id: user.id,
      login: user.login,
      password: user.password,
      name: user.name,
      birthDate: user.birthDate || null,
      age: user.age,
      salary: user.salary,
      roles: user.roles.map((role) => String(role.id)),
    };
  }

  private toPasswordChangeFormDTO(oldPassword: string, newPassword: string) {
    return {
      oldPassword: oldPassword,
      newPassword: newPassword,
    };
  }
}
