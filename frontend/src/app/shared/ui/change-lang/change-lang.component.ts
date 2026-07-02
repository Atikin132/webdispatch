import { Component, inject } from '@angular/core';
import { TranslatePipe, TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-change-lang',
  imports: [TranslatePipe],
  templateUrl: './change-lang.component.html',
  styleUrl: './change-lang.component.scss',
})
export class ChangeLangComponent {
  private translate = inject(TranslateService);

  constructor() {
    const savedLang = localStorage.getItem('lang') || 'en';
    this.translate.use(savedLang);
  }

  changeLanguage(language: 'en' | 'ru') {
    this.translate.use(language);
    localStorage.setItem('lang', language);
  }
}
