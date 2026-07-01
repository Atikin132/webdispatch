import { Component, computed, inject, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { InputText } from 'primeng/inputtext';
import { Password } from 'primeng/password';
import { Button } from 'primeng/button';
import { ActivatedRoute, Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { ROLES } from '../../../core/mock/users.mock';
import { Checkbox } from 'primeng/checkbox';
import { InputNumber } from 'primeng/inputnumber';
import { DatePicker } from 'primeng/datepicker';
import { UserService } from '../../../core/services/user.service';
import { User } from '../../../core/models/user';
import { Role } from '../../../core/models/role';

@Component({
  selector: 'app-user-form',
  imports: [ReactiveFormsModule, InputText, Password, Button, Checkbox, InputNumber, DatePicker],
  templateUrl: './user-form.html',
  styleUrl: './user-form.scss',
})
export class UserForm {
  private fb = inject(FormBuilder);
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private toast = inject(MessageService);
  private userService = inject(UserService);

  roles = ROLES;

  mode = signal<'add' | 'edit'>('add');

  title = computed(() => (this.mode() === 'add' ? 'Add user' : 'Edit user'));

  userForm = this.fb.group({
    id: this.fb.control<number | null>(null),
    login: this.fb.control('', { nonNullable: true, validators: [Validators.required] }),
    password: this.fb.control('', {
      nonNullable: true,
      validators: [Validators.required, Validators.minLength(6)],
    }),
    name: this.fb.control('', { nonNullable: true, validators: [Validators.required] }),
    birthDate: this.fb.control<Date | null>(null),
    age: this.fb.control<number>(0, {
      nonNullable: true,
      validators: [Validators.required, Validators.min(19)],
    }),
    salary: this.fb.control<number>(0, {
      nonNullable: true,
      validators: [Validators.required],
    }),
    roles: this.fb.control<Role[]>([], {
      nonNullable: true,
      validators: [Validators.required],
    }),
  });

  constructor() {
    const id = Number(this.route.snapshot.paramMap.get('id'));

    if (id) {
      this.mode.set('edit');
      this.loadUser(id);
    }
  }

  private loadUser(id: number) {
    const user = this.userService.getUser(id);

    if (!user) {
      return;
    }

    this.userForm.patchValue({
      id: user.id,
      login: user.login,
      password: user.password,
      name: user.name,
      birthDate: user.birthDate ? new Date(user.birthDate) : null,
      age: user.age,
      salary: user.salary,
      roles: user.roles,
    });
  }

  private formatDate(date: Date): string {
    return date.toISOString().split('T')[0];
  }

  save() {
    if (this.userForm.invalid) {
      this.userForm.markAllAsTouched();
      this.toast.add({
        severity: 'error',
        summary: 'Validation Error',
        detail: this.getValidationErrorMessage(),
      });
      return;
    }

    const form = this.userForm.getRawValue();
    const user: User = {
      id: form.id ?? 0,
      login: form.login,
      password: form.password,
      name: form.name,
      birthDate: form.birthDate ? this.formatDate(form.birthDate) : '',
      age: form.age,
      salary: form.salary,
      roles: form.roles,
    };

    const errorMessage = this.userService.validateUser(user);
    if (errorMessage) {
      this.toast.add({
        severity: 'error',
        summary: 'Validation',
        detail: errorMessage,
      });
      return;
    }

    if (this.mode() === 'add') {
      this.userService.create(user);
      this.toast.add({
        severity: 'success',
        summary: 'Created',
        detail: 'User added',
      });
    } else {
      this.userService.update(user);
      this.toast.add({
        severity: 'success',
        summary: 'Updated',
        detail: 'User updated',
      });
    }
    void this.router.navigate(['/users']);
  }

  private getValidationErrorMessage(): string {
    const controls = this.userForm.controls;
    if (controls.age.hasError('min')) {
      return 'Age must be older 18';
    }
    if (controls.password.hasError('minlength')) {
      return 'Password must be at least 6 characters long';
    }
    if (controls.login.hasError('required')) {
      return 'Login is required';
    }
    if (controls.name.hasError('required')) {
      return 'Name is required';
    }
    if (controls.age.hasError('required')) {
      return 'Age is required';
    }
    if (controls.salary.hasError('required')) {
      return 'Salary is required';
    }
    if (controls.roles.hasError('required')) {
      return 'At least one role must be selected';
    }
    return 'Please fill all required fields correctly';
  }

  back() {
    void this.router.navigate(['/users']);
  }
}
