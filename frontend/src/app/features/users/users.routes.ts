import { Routes } from '@angular/router';
import { UsersComponent } from './users.component';
import { UserFormComponent } from './user-form/user-form.component';

export const usersRoutes: Routes = [
  {
    path: '',
    component: UsersComponent,
  },
  {
    path: 'edit/:id',
    component: UserFormComponent,
  },
  {
    path: 'add',
    component: UserFormComponent,
  },
];
