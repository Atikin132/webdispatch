import { Component, inject } from '@angular/core';
import { Password } from 'primeng/password';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Button } from 'primeng/button';
import { MessageService } from 'primeng/api';
import { AuthService } from '../../core/services/auth-service';

@Component({
  selector: 'app-loginedit',
  imports: [Password, ReactiveFormsModule, Button],
  templateUrl: './loginedit.html',
  styleUrl: './loginedit.scss',
})
export class Loginedit {
  private fb = inject(FormBuilder);
  private messageService = inject(MessageService);
  private authService = inject(AuthService);

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
        summary: 'Success',
        detail: 'Password changed successfully',
      });

      this.passwordForm.reset();
    } else {
      this.messageService.add({
        severity: 'error',
        summary: 'Error',
        detail: 'Old password is incorrect',
      });
    }
  }
}
