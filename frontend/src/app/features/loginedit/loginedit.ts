import { Component, inject } from '@angular/core';
import { Password } from 'primeng/password';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Button } from 'primeng/button';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'app-loginedit',
  imports: [Password, ReactiveFormsModule, Button],
  templateUrl: './loginedit.html',
  styleUrl: './loginedit.scss',
})
export class Loginedit {
  private fb = inject(FormBuilder);
  private messageService = inject(MessageService);

  passwordForm = this.fb.group({
    oldPassword: ['', Validators.required],
    newPassword: ['', Validators.required],
  });

  changePassword(): void {
    if (this.passwordForm.invalid) {
      this.passwordForm.markAllAsTouched();
      return;
    }

    this.messageService.add({
      severity: 'success',
      summary: 'Success',
      detail: 'Password changed successfully',
    });
  }
}
