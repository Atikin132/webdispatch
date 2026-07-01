import { Component, computed, inject } from '@angular/core';
import { NavigationEnd, Router, RouterLink } from '@angular/router';
import { TabsModule } from 'primeng/tabs';
import { toSignal } from '@angular/core/rxjs-interop';
import { filter, map, startWith } from 'rxjs';
import { AuthService } from '../../../services/auth.service';
import { TranslatePipe } from '@ngx-translate/core';

@Component({
  selector: 'app-menu',
  imports: [RouterLink, TabsModule, TranslatePipe],
  templateUrl: './menu.html',
  styleUrl: './menu.scss',
})
export class Menu {
  private router = inject(Router);
  private authService = inject(AuthService);

  isAdmin = computed(() => this.authService.hasRole('Administrator'));

  private currentUrl = toSignal(
    this.router.events.pipe(
      filter((event) => event instanceof NavigationEnd),
      map(() => this.router.url),
      startWith(this.router.url),
    ),
    { initialValue: this.router.url },
  );

  activeTab = computed(() => this.currentUrl());
}
