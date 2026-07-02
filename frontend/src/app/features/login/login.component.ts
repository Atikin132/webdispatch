import { Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Password } from 'primeng/password';
import { Button } from 'primeng/button';
import { ChangeLangComponent } from '../../shared/ui/change-lang/change-lang.component';
import { InputText } from 'primeng/inputtext';
import { MessageService } from 'primeng/api';
import { Router } from '@angular/router';
import { AuthService } from '../../core/services/auth.service';
import { TranslatePipe, TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-login',
  imports: [ReactiveFormsModule, Password, Button, ChangeLangComponent, InputText, TranslatePipe],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
})
export class LoginComponent {
  private fb = inject(FormBuilder);
  private messageService = inject(MessageService);
  private authService = inject(AuthService);
  private router = inject(Router);
  private translate = inject(TranslateService);

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
        summary: this.translate.instant('loginErrorSummary'),
        detail: this.translate.instant('loginErrorDetail'),
      });
      return;
    }
    void this.router.navigate(['/welcome']);
  }
}
