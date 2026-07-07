import { Component, DestroyRef, inject } from '@angular/core';
import { TableModule } from 'primeng/table';
import { Tag } from 'primeng/tag';
import { Button } from 'primeng/button';
import { RouterLink } from '@angular/router';
import { AuthService } from '../../core/services/auth.service';
import { UserService } from '../../core/services/user.service';
import { TranslatePipe, TranslateService } from '@ngx-translate/core';
import { ConfirmationService, MessageService } from 'primeng/api';
import { ConfirmDialog } from 'primeng/confirmdialog';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';

@Component({
  selector: 'app-users',
  imports: [TableModule, Tag, Button, RouterLink, TranslatePipe, ConfirmDialog],
  templateUrl: './users.component.html',
  styleUrl: './users.component.scss',
})
export class UsersComponent {
  private authService = inject(AuthService);
  private userService = inject(UserService);
  private confirmationService = inject(ConfirmationService);
  private translate = inject(TranslateService);
  private toast = inject(MessageService);
  private destroyRef = inject(DestroyRef);

  users = this.userService.getUsers();

  currentUser = this.authService.currentUser;

  deleteUser(userId: number): void {
    this.confirmationService.confirm({
      header: this.translate.instant('usersDeleteHeader'),
      message: this.translate.instant('usersDeleteMessage'),
      acceptLabel: this.translate.instant('yes'),
      rejectLabel: this.translate.instant('no'),
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.userService
          .delete(userId)
          .pipe(takeUntilDestroyed(this.destroyRef))
          .subscribe({
            next: () => {
              this.toast.add({
                severity: 'success',
                summary: this.translate.instant('userFormDeleteSuccessSummary'),
                detail: this.translate.instant('userFormDeleteSuccessDetail'),
              });
            },
            error: () => {
              this.toast.add({
                severity: 'error',
                summary: this.translate.instant('userFormDeleteErrorSummary'),
                detail: this.translate.instant('userFormDeleteErrorDetail'),
              });
            },
          });
      },
    });
  }
}
