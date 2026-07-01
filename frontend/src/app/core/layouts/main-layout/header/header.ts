import { Component, inject } from '@angular/core';
import { ButtonModule } from 'primeng/button';
import { ChangeLang } from '../../change-lang/change-lang';
import { Router } from '@angular/router';
import { AuthService } from '../../../services/auth.service';

@Component({
  selector: 'app-header',
  imports: [ButtonModule, ChangeLang],
  templateUrl: './header.html',
  styleUrl: './header.scss',
})
export class Header {
  private authService = inject(AuthService);
  private router = inject(Router);

  currentUser = this.authService.currentUserSignal;

  onLogout(): void {
    this.authService.logout();
    void this.router.navigate(['/login']);
  }
}
