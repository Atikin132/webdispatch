import { Component, computed, effect, inject, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { InputText } from 'primeng/inputtext';
import { Password } from 'primeng/password';
import { Button } from 'primeng/button';
import { ActivatedRoute, Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { Checkbox } from 'primeng/checkbox';
import { InputNumber } from 'primeng/inputnumber';
import { DatePicker } from 'primeng/datepicker';
import { UserService } from '../../../core/services/user.service';
import { User } from '../../../core/models/user';
import { TranslatePipe, TranslateService } from '@ngx-translate/core';
import { RoleService } from '../../../core/services/role.service';

@Component({
  selector: 'app-user-form',
  imports: [
    ReactiveFormsModule,
    InputText,
    Password,
    Button,
    Checkbox,
    InputNumber,
    DatePicker,
    TranslatePipe,
  ],
  templateUrl: './user-form.component.html',
  styleUrl: './user-form.component.scss',
})
export class UserFormComponent {
  private fb = inject(FormBuilder);
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private toast = inject(MessageService);
  private userService = inject(UserService);
  private roleService = inject(RoleService);
  private translate = inject(TranslateService);

  roles = this.roleService.getRoles();

  mode = signal<'add' | 'edit'>('add');

  title = computed(() =>
    this.mode() === 'add'
      ? this.translate.instant('addUserTitle')
      : this.translate.instant('editUserTitle'),
  );

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
    roles: this.fb.control<number[]>([], {
      nonNullable: true,
      validators: [Validators.required],
    }),
  });

  constructor() {
    const id = Number(this.route.snapshot.paramMap.get('id'));

    if (id) {
      this.mode.set('edit');
      effect(() => {
        const availableRoles = this.roles();
        if (availableRoles && availableRoles.length > 0) {
          this.loadUser(id);
        }
      });
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
      roles: user.roles.map((role) => role.id),
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
        summary: this.translate.instant('userFormValidationErrorSummary'),
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
      roles: form.roles.map((id) => this.roles().find((r) => r.id === id)!).filter(Boolean),
    };

    const errorMessage = this.userService.validateUser(user);
    if (errorMessage) {
      this.toast.add({
        severity: 'error',
        summary: this.translate.instant('userFormValidationErrorSummary'),
        detail: errorMessage,
      });
      return;
    }

    if (this.mode() === 'add') {
      this.userService.create(user);
      this.toast.add({
        severity: 'success',
        summary: this.translate.instant('userFormCreateSuccessSummary'),
        detail: this.translate.instant('userFormCreateSuccessDetail'),
      });
    } else {
      this.userService.update(user);
      this.toast.add({
        severity: 'success',
        summary: this.translate.instant('userFormUpdateSuccessSummary'),
        detail: this.translate.instant('userFormUpdateSuccessDetail'),
      });
    }
    void this.router.navigate(['/users']);
  }

  private getValidationErrorMessage(): string {
    const controls = this.userForm.controls;
    if (controls.login.hasError('required')) {
      return this.translate.instant('validationLoginRequired');
    }
    if (controls.password.hasError('minlength')) {
      return this.translate.instant('validationPasswordTooShort');
    }
    if (controls.name.hasError('required')) {
      return this.translate.instant('validationNameRequired');
    }
    if (controls.age.hasError('required')) {
      return this.translate.instant('validationAgeRequired');
    }
    if (controls.age.hasError('min')) {
      return this.translate.instant('validationAgeTooYoung');
    }
    if (controls.salary.hasError('required')) {
      return this.translate.instant('validationSalaryRequired');
    }
    if (controls.roles.hasError('required')) {
      return this.translate.instant('validationRolesOneRequired');
    }
    return this.translate.instant('validationFillAllRequired');
  }

  back() {
    void this.router.navigate(['/users']);
  }
}
