import { Component, inject } from '@angular/core';
import { Password } from 'primeng/password';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Button } from 'primeng/button';
import { AuthService } from '../../core/services/auth.service';
import { TranslatePipe, TranslateService } from '@ngx-translate/core';
import { MessageService } from 'primeng/api';
import { UserService } from '../../core/services/user.service';

@Component({
  selector: 'app-login-edit',
  imports: [Password, ReactiveFormsModule, Button, TranslatePipe],
  templateUrl: './login-edit.component.html',
  styleUrl: './login-edit.component.scss',
})
export class LoginEditComponent {
  private fb = inject(FormBuilder);
  private authService = inject(AuthService);
  private userService = inject(UserService);
  private toast = inject(MessageService);
  private translate = inject(TranslateService);

  passwordForm = this.fb.group({
    oldPassword: ['', Validators.required],
    newPassword: ['', Validators.required],
  });

  changePassword(): void {
    if (this.passwordForm.invalid) {
      this.passwordForm.markAllAsTouched();
      return;
    }
    const { oldPassword, newPassword } = this.passwordForm.value;
    const userId = this.authService.getCurrentUserId();
    if (!oldPassword || !newPassword || userId === null) {
      return;
    }
    this.userService.updatePassword(userId, oldPassword, newPassword).subscribe({
      next: () => {
        this.toast.add({
          severity: 'success',
          summary: this.translate.instant('logineditSuccessSummary'),
          detail: this.translate.instant('logineditSuccessDetail'),
        });

        this.passwordForm.reset();
      },
      error: () => {
        this.toast.add({
          severity: 'error',
          summary: this.translate.instant('logineditErrorSummary'),
          detail: this.translate.instant('logineditErrorDetail'),
        });
      },
    });
  }
}
