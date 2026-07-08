import { ApplicationConfig, provideBrowserGlobalErrorListeners } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import { providePrimeNG } from 'primeng/config';
import { ThemePreset } from './theme.config';
import { ConfirmationService, MessageService } from 'primeng/api';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { provideTranslateService, TranslateLoader } from '@ngx-translate/core';
import { provideTranslateHttpLoader, TranslateHttpLoader } from '@ngx-translate/http-loader';
import { jwtInterceptor } from './core/interceptors/jwt.interceptor';

export const appConfig: ApplicationConfig = {
  providers: [
    provideBrowserGlobalErrorListeners(),
    provideRouter(routes),
    MessageService,
    providePrimeNG({
      theme: {
        preset: ThemePreset,
      },
    }),
    provideHttpClient(withInterceptors([jwtInterceptor])),
    provideTranslateService({
      loader: {
        provide: TranslateLoader,
        useClass: TranslateHttpLoader,
      },
    }),
    provideTranslateHttpLoader({
      prefix: './i18n/',
      suffix: '.json',
    }),
    ConfirmationService,
  ],
};
