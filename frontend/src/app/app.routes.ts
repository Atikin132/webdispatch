import { Routes } from '@angular/router';
import { WelcomeComponent } from './features/welcome/welcome.component';
import { LoginEditComponent } from './features/login-edit/login-edit.component';
import { LoginComponent } from './features/login/login.component';
import { MainLayoutComponent } from './core/layouts/main-layout/main-layout.component';
import { AuthLayoutComponent } from './core/layouts/auth-layout/auth-layout.component';
import { authGuard } from './core/guards/auth.guard';
import { guestGuard } from './core/guards/guest.guard';
import { adminGuard } from './core/guards/admin.guard';

export const routes: Routes = [
  {
    path: '',
    component: MainLayoutComponent,
    children: [
      {
        path: '',
        redirectTo: 'welcome',
        pathMatch: 'full',
      },
      {
        path: 'welcome',
        component: WelcomeComponent,
        canActivate: [authGuard],
      },
      {
        path: 'users',
        loadChildren: () => import('./features/users/users.routes').then((m) => m.usersRoutes),
        canMatch: [authGuard, adminGuard],
      },
      {
        path: 'loginedit',
        component: LoginEditComponent,
        canActivate: [authGuard],
      },
    ],
  },
  {
    path: '',
    component: AuthLayoutComponent,
    children: [
      {
        path: 'login',
        component: LoginComponent,
        canActivate: [guestGuard],
      },
    ],
  },
  {
    path: '**',
    redirectTo: 'welcome',
  },
];
