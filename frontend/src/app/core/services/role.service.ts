import { computed, inject, Injectable } from '@angular/core';
import { Role } from '../models/role';
import { HttpClient } from '@angular/common/http';
import { MessageService } from 'primeng/api';
import { TranslateService } from '@ngx-translate/core';
import { rxResource } from '@angular/core/rxjs-interop';
import { catchError } from 'rxjs';
import { RoleTranslateService } from './role-translate.service';
import { environment } from '../environment';

@Injectable({ providedIn: 'root' })
export class RoleService {
  private http = inject(HttpClient);
  private toast = inject(MessageService);
  private translate = inject(TranslateService);
  private roleTranslate = inject(RoleTranslateService);
  private readonly apiUrl = environment.apiUrl + '/roles';

  private rolesResource = rxResource({
    stream: () =>
      this.http.get<Role[]>(this.apiUrl).pipe(
        catchError(() => {
          this.toast.add({
            severity: 'error',
            summary: this.translate.instant('loadErrorSummary'),
            detail: this.translate.instant('loadRolesErrorDetail'),
          });
          return [];
        }),
      ),
  });

  readonly roles = computed(() => {
    const rawRoles = this.rolesResource.value() ?? [];
    this.roleTranslate.langSignal();
    return this.roleTranslate.translateRoles(rawRoles);
  });

  public getRoles() {
    return this.roles;
  }
}
