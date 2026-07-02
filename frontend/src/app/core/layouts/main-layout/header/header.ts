import { Component, inject } from '@angular/core';
import { ButtonModule } from 'primeng/button';
import { ChangeLang } from '../../../../shared/ui/change-lang/change-lang';
import { Router } from '@angular/router';
import { AuthService } from '../../../services/auth.service';
import { TranslatePipe } from '@ngx-translate/core';

@Component({
  selector: 'app-header',
  imports: [ButtonModule, ChangeLang, TranslatePipe],
  templateUrl: './header.html',
  styleUrl: './header.scss',
})
export class Header {
  private authService = inject(AuthService);
  private router = inject(Router);

  currentUser = this.authService.currentUser;

  onLogout(): void {
    this.authService.logout();
    void this.router.navigate(['/login']);
  }
}
