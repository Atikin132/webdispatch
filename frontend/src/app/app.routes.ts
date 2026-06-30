import { Routes } from '@angular/router';
import { Welcome } from './features/welcome/welcome';
import { Loginedit } from './features/loginedit/loginedit';
import { Login } from './features/login/login';
import { MainLayout } from './core/layouts/main-layout/main-layout';
import { AuthLayout } from './core/layouts/auth-layout/auth-layout';

export const routes: Routes = [
  {
    path: '',
    component: MainLayout,
    children: [
      {
        path: '',
        redirectTo: 'welcome',
        pathMatch: 'full',
      },
      {
        path: 'welcome',
        component: Welcome,
      },
      {
        path: 'users',
        loadChildren: () => import('./features/users/users.routes').then((m) => m.usersRoutes),
      },
      {
        path: 'loginedit',
        component: Loginedit,
      },
    ],
  },
  {
    path: '',
    component: AuthLayout,
    children: [
      {
        path: 'login',
        component: Login,
      },
    ],
  },
  {
    path: '**',
    redirectTo: 'welcome',
  },
];
