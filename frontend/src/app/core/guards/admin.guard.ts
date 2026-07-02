import { inject } from '@angular/core';
import { CanMatchFn, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { ROLES } from '../constants/roles.constants';

export const adminGuard: CanMatchFn = () => {
  const authService = inject(AuthService);
  const router = inject(Router);

  if (authService.hasRole(ROLES.ADMINISTRATOR)) {
    return true;
  }

  return router.createUrlTree(['/welcome']);
};
