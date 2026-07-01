import { Component, inject } from '@angular/core';
import { AuthService } from '../../core/services/auth.service';
import { TranslatePipe } from '@ngx-translate/core';

@Component({
  selector: 'app-welcome',
  imports: [TranslatePipe],
  templateUrl: './welcome.html',
  styleUrl: './welcome.scss',
})
export class Welcome {
  private authService = inject(AuthService);

  currentUser = this.authService.currentUserSignal;
}
