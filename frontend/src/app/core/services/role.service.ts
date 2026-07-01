import { Injectable, signal } from '@angular/core';
import { ROLES } from '../mock/users.mock';
import { Role } from '../models/role';

@Injectable({ providedIn: 'root' })
export class RoleService {
  private roles = signal<Role[]>([...ROLES]);

  getRoles() {
    return this.roles;
  }
}
