import { Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Password } from 'primeng/password';
import { Button } from 'primeng/button';
import { ChangeLang } from '../../core/layouts/change-lang/change-lang';
import { InputText } from 'primeng/inputtext';
import { MessageService } from 'primeng/api';
import { Router } from '@angular/router';
import { AuthService } from '../../core/services/auth.service';

@Component({
  selector: 'app-login',
  imports: [ReactiveFormsModule, Password, Button, ChangeLang, InputText],
  templateUrl: './login.html',
  styleUrl: './login.scss',
})
export class Login {
  private fb = inject(FormBuilder);
  private messageService = inject(MessageService);
  private authService = inject(AuthService);
  private router = inject(Router);

  loginForm = this.fb.group({
    login: ['', Validators.required],
    password: ['', Validators.required],
  });

  login(): void {
    if (this.loginForm.invalid) {
      this.loginForm.markAllAsTouched();
      return;
    }

    const { login, password } = this.loginForm.getRawValue();
    if (!login || !password) {
      return;
    }
    const success = this.authService.login(login, password);
    if (!success) {
      this.messageService.add({
        severity: 'error',
        summary: 'Login failed',
        detail: 'Invalid login or password',
      });
      return;
    }
    void this.router.navigate(['/welcome']);
  }
}
