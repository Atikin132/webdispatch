import { Routes } from '@angular/router';
import { Users } from './users';
import { UserForm } from './user-form/user-form';

export const usersRoutes: Routes = [
  {
    path: '',
    component: Users,
  },
  {
    path: 'edit/:id',
    component: UserForm,
  },
  {
    path: 'add',
    component: UserForm,
  },
];
