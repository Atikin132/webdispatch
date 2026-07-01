import { Routes } from '@angular/router';
import { Welcome } from './features/welcome/welcome';
import { Loginedit } from './features/loginedit/loginedit';
import { Login } from './features/login/login';
import { MainLayout } from './core/layouts/main-layout/main-layout';
import { AuthLayout } from './core/layouts/auth-layout/auth-layout';
import { authGuard } from './core/guards/auth.guard';
import { guestGuard } from './core/guards/guest.guard';
import { adminGuard } from './core/guards/admin.guard';

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
        canActivate: [authGuard],
      },
      {
        path: 'users',
        loadChildren: () => import('./features/users/users.routes').then((m) => m.usersRoutes),
        canMatch: [authGuard, adminGuard],
      },
      {
        path: 'loginedit',
        component: Loginedit,
        canActivate: [authGuard],
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
        canActivate: [guestGuard],
      },
    ],
  },
  {
    path: '**',
    redirectTo: 'welcome',
  },
];
