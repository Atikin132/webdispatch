import { computed, inject, Injectable, signal } from '@angular/core';
import { User } from '../models/user';
import { UserService } from './user.service';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { TranslateService } from '@ngx-translate/core';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private userService = inject(UserService);
  private router = inject(Router);
  private messageService = inject(MessageService);
  private translate = inject(TranslateService);

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

  login(login: string, password: string): void {
    this.userService.login(login, password).subscribe({
      next: (user: User) => {
        if (user) {
          this.currentUserId.set(user.id);
          localStorage.setItem('currentUserId', user.id.toString());
          void this.router.navigate(['/welcome']);
        }
      },
      error: () => {
        this.messageService.add({
          severity: 'error',
          summary: this.translate.instant('loginErrorSummary'),
          detail: this.translate.instant('loginErrorDetail'),
        });
      },
    });
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
