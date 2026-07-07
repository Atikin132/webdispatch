import { computed, inject, Injectable } from '@angular/core';
import { User } from '../models/user';
import { TranslateService } from '@ngx-translate/core';
import { RoleTranslateService } from './role-translate.service';
import { HttpClient } from '@angular/common/http';
import { MessageService } from 'primeng/api';
import { rxResource } from '@angular/core/rxjs-interop';
import { catchError, EMPTY, tap } from 'rxjs';
import { environment } from '../environment';

@Injectable({ providedIn: 'root' })
export class UserService {
  private http = inject(HttpClient);
  private toast = inject(MessageService);
  private translate = inject(TranslateService);
  private roleTranslate = inject(RoleTranslateService);

  private readonly apiUrl = environment.apiUrl + '/users';

  readonly usersResource = rxResource({
    stream: () =>
      this.http.get<User[]>(this.apiUrl).pipe(
        catchError(() => {
          this.toast.add({
            severity: 'error',
            summary: this.translate.instant('loadErrorSummary'),
            detail: this.translate.instant('loadUsersErrorDetail'),
          });
          return EMPTY;
        }),
      ),
  });

  readonly usersTranslated = computed(() => {
    this.roleTranslate.langSignal();
    return this.roleTranslate.translateUsersRoles(this.usersResource.value() ?? []);
  });

  getUsers() {
    return this.usersTranslated;
  }

  getUser(id: number): User | undefined {
    return this.usersResource.value()?.find((user) => user.id === id);
  }

  create(user: User) {
    return this.http
      .post<User>(this.apiUrl, this.toUserFormDTO(user))
      .pipe(tap(() => this.usersResource.reload()));
  }

  update(user: User) {
    return this.http
      .put<User>(`${this.apiUrl}/${user.id}`, this.toUserFormDTO(user))
      .pipe(tap(() => this.usersResource.reload()));
  }

  delete(id: number) {
    return this.http
      .delete<void>(`${this.apiUrl}/${id}`)
      .pipe(tap(() => this.usersResource.reload()));
  }

  loginExists(login: string, currentId?: number): boolean {
    return (this.usersResource.value() ?? []).some(
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

  getUserByLoginAndPassword(login: string, password: string): User | undefined {
    return this.usersResource
      .value()
      ?.find((user) => user.login === login && user.password === password);
  }

  updatePassword(userId: number, oldPassword: string, newPassword: string) {
    return this.http
      .put<{ message: string }>(
        `${this.apiUrl}/${userId}/password`,
        this.toPasswordChangeFormDTO(oldPassword, newPassword),
      )
      .pipe(
        tap(() => {
          this.usersResource.reload();
          this.toast.add({
            severity: 'success',
            summary: this.translate.instant('logineditSuccessSummary'),
            detail: this.translate.instant('logineditSuccessDetail'),
          });
        }),
        catchError((error) => {
          this.toast.add({
            severity: 'error',
            summary: this.translate.instant('logineditErrorSummary'),
            detail: this.translate.instant('logineditErrorDetail'),
          });
          throw error;
        }),
      );
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
