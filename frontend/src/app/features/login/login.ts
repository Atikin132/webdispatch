import { Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Password } from 'primeng/password';
import { Button } from 'primeng/button';
import { ChangeLang } from '../../core/layouts/change-lang/change-lang';
import { InputText } from 'primeng/inputtext';
import { MessageService } from 'primeng/api';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-login',
  imports: [ReactiveFormsModule, Password, Button, ChangeLang, InputText, RouterLink],
  templateUrl: './login.html',
  styleUrl: './login.scss',
})
export class Login {
  private fb = inject(FormBuilder);
  private messageService = inject(MessageService);

  loginForm = this.fb.group({
    login: ['', Validators.required],
    password: ['', Validators.required],
  });

  login(): void {
    this.messageService.add({
      severity: 'error',
      summary: 'Error',
      detail: 'Error',
    });
    if (this.loginForm.invalid) {
      this.loginForm.markAllAsTouched();
      return;
    }
  }
}
