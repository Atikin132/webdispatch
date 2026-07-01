import { Component, signal } from '@angular/core';
import { ButtonModule } from 'primeng/button';
import { ChangeLang } from '../../change-lang/change-lang';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-header',
  imports: [ButtonModule, ChangeLang, RouterLink],
  templateUrl: './header.html',
  styleUrl: './header.scss',
})
export class Header {
  currentUser = signal('USER');

  onLogout(): void {
    //TODO Logout
  }
}
