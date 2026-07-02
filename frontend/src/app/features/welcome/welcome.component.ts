import { Component, inject } from '@angular/core';
import { AuthService } from '../../core/services/auth.service';
import { TranslatePipe } from '@ngx-translate/core';

@Component({
  selector: 'app-welcome',
  imports: [TranslatePipe],
  templateUrl: './welcome.component.html',
  styleUrl: './welcome.component.scss',
})
export class WelcomeComponent {
  private authService = inject(AuthService);

  currentUser = this.authService.currentUser;
}
