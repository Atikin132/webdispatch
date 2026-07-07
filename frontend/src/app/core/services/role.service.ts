import { inject, Injectable, signal } from '@angular/core';
import { Role } from '../models/role';
import { HttpClient } from '@angular/common/http';
import { MessageService } from 'primeng/api';
import { TranslateService } from '@ngx-translate/core';

@Injectable({ providedIn: 'root' })
export class RoleService {
  private http = inject(HttpClient);
  private toast = inject(MessageService);
  private translate = inject(TranslateService);
  private readonly apiUrl = 'http://localhost:8080/roles';

  private roles = signal<Role[]>([]);

  constructor() {
    this.loadRoles();
  }

  private loadRoles(): void {
    this.http.get<Role[]>(this.apiUrl).subscribe({
      next: (roles) => this.roles.set(roles),
      error: () => {
        this.toast.add({
          severity: 'error',
          summary: this.translate.instant('loadErrorSummary'),
          detail: this.translate.instant('loadRolesErrorDetail'),
        });
      },
    });
  }

  getRoles() {
    return this.roles.asReadonly();
  }
}
