import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Header } from './header/header';
import { Footer } from './footer/footer';
import { Menu } from './menu/menu';

@Component({
  selector: 'app-main-layout',
  imports: [RouterOutlet, Header, Footer, Menu],
  templateUrl: './main-layout.html',
  styleUrl: './main-layout.scss',
})
export class MainLayout {}
