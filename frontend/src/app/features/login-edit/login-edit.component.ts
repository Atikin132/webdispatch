import { Component, inject } from '@angular/core';
import { Password } from 'primeng/password';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Button } from 'primeng/button';
import { AuthService } from '../../core/services/auth.service';
import { TranslatePipe } from '@ngx-translate/core';

@Component({
  selector: 'app-login-edit',
  imports: [Password, ReactiveFormsModule, Button, TranslatePipe],
  templateUrl: './login-edit.component.html',
  styleUrl: './login-edit.component.scss',
})
export class LoginEditComponent {
  private fb = inject(FormBuilder);
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
    this.authService.changePassword(oldPassword, newPassword);
  }
}
