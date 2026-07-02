import { Component, inject } from '@angular/core';
import { Password } from 'primeng/password';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Button } from 'primeng/button';
import { MessageService } from 'primeng/api';
import { AuthService } from '../../core/services/auth.service';
import { TranslatePipe, TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-login-edit',
  imports: [Password, ReactiveFormsModule, Button, TranslatePipe],
  templateUrl: './login-edit.component.html',
  styleUrl: './login-edit.component.scss',
})
export class LoginEditComponent {
  private fb = inject(FormBuilder);
  private messageService = inject(MessageService);
  private authService = inject(AuthService);
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
    if (!oldPassword || !newPassword) {
      return;
    }
    const result = this.authService.changePassword(oldPassword, newPassword);

    if (result) {
      this.messageService.add({
        severity: 'success',
        summary: this.translate.instant('logineditSuccessSummary'),
        detail: this.translate.instant('logineditSuccessDetail'),
      });

      this.passwordForm.reset();
    } else {
      this.messageService.add({
        severity: 'error',
        summary: this.translate.instant('logineditErrorSummary'),
        detail: this.translate.instant('logineditErrorDetail'),
      });
    }
  }
}
