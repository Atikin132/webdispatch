import { Component, computed, inject, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { InputText } from 'primeng/inputtext';
import { Password } from 'primeng/password';
import { Button } from 'primeng/button';
import { ActivatedRoute, Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { ROLES } from '../mock/users.mock';
import { Checkbox } from 'primeng/checkbox';
import { InputNumber } from 'primeng/inputnumber';
import { DatePicker } from 'primeng/datepicker';

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

  roles = ROLES;

  mode = signal<'add' | 'edit'>('add');

  title = computed(() => (this.mode() === 'add' ? 'Add user' : 'Edit user'));

  userForm = this.fb.group({
    id: [null],
    login: ['', Validators.required],
    password: ['', Validators.required],
    name: ['', Validators.required],
    birthDate: [null],
    age: [null, Validators.required],
    salary: [null, Validators.required],
    roles: [[], Validators.required],
  });

  constructor() {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.mode.set('edit');
    }
  }
  //TODO loadUser
  //   loadUser(id: number) {
  //   }

  save() {
    if (this.userForm.invalid) {
      this.userForm.markAllAsTouched();
      this.toast.add({
        severity: 'error',
        summary: 'Validation error',
        detail: 'Fill all required fields',
      });
      return;
    }

    if (this.mode() === 'add') {
      this.toast.add({
        severity: 'success',
        summary: 'Created',
        detail: 'User added',
      });
    } else {
      this.toast.add({
        severity: 'success',
        summary: 'Updated',
        detail: 'User updated',
      });
    }
  }

  back() {
    void this.router.navigate(['/users']);
  }
}
