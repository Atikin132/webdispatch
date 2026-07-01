import { Injectable, inject } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { Role } from '../models/role';
import { User } from '../models/user';

@Injectable({ providedIn: 'root' })
export class RoleTranslateService {
  private translate = inject(TranslateService);

  translateRole(role: Role): Role {
    return {
      ...role,
      displayName: this.translate.instant(`role${role.name}`),
    };
  }

  translateRoles(roles: Role[]): Role[] {
    return roles.map((role) => this.translateRole(role));
  }

  translateUserRoles(user: User): User {
    return {
      ...user,
      roles: this.translateRoles(user.roles),
    };
  }

  translateUsersRoles(users: User[]): User[] {
    return users.map((user) => this.translateUserRoles(user));
  }
}
