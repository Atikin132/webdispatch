import { Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Password } from 'primeng/password';
import { Button } from 'primeng/button';
import { ChangeLangComponent } from '../../shared/ui/change-lang/change-lang.component';
import { InputText } from 'primeng/inputtext';
import { AuthService } from '../../core/services/auth.service';
import { TranslatePipe, TranslateService } from '@ngx-translate/core';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'app-login',
  imports: [ReactiveFormsModule, Password, Button, ChangeLangComponent, InputText, TranslatePipe],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
})
export class LoginComponent {
  private fb = inject(FormBuilder);
  private authService = inject(AuthService);
  private router = inject(Router);
  private messageService = inject(MessageService);
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

    this.authService.login(login, password).subscribe({
      next: (response) => {
        localStorage.setItem('token', response.token);
        localStorage.setItem('currentUserId', response.id.toString());
        localStorage.setItem('login', response.login);
        localStorage.setItem('roles', JSON.stringify(response.roles));
        void this.router.navigate(['/welcome']);
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
}
