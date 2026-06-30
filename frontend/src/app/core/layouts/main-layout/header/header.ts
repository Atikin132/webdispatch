import { Component, signal } from '@angular/core';
import { ButtonModule } from 'primeng/button';
import { ChangeLang } from '../../change-lang/change-lang';

@Component({
  selector: 'app-header',
  imports: [ButtonModule, ChangeLang],
  templateUrl: './header.html',
  styleUrl: './header.scss',
})
export class Header {
  currentUser = signal('USER');

  onLogout(): void {
    //TODO Logout
  }
}
