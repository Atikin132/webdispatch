import { Component, inject } from '@angular/core';
import { TranslatePipe, TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-change-lang',
  imports: [TranslatePipe],
  templateUrl: './change-lang.html',
  styleUrl: './change-lang.scss',
})
export class ChangeLang {
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
