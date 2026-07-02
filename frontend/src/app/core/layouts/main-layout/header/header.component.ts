import { Component, inject } from '@angular/core';
import { ButtonModule } from 'primeng/button';
import { ChangeLangComponent } from '../../../../shared/ui/change-lang/change-lang.component';
import { Router } from '@angular/router';
import { AuthService } from '../../../services/auth.service';
import { TranslatePipe } from '@ngx-translate/core';

@Component({
  selector: 'app-header',
  imports: [ButtonModule, ChangeLangComponent, TranslatePipe],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss',
})
export class HeaderComponent {
  private authService = inject(AuthService);
  private router = inject(Router);

  currentUser = this.authService.currentUser;

  onLogout(): void {
    this.authService.logout();
    void this.router.navigate(['/login']);
  }
}
