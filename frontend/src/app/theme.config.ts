import { definePreset } from '@primeuix/themes';
import Material from '@primeuix/themes/material';

export const ThemePreset = definePreset(Material, {
  semantic: {
    primary: {
      50: '#f4f7fd',
      100: '#e7effb',
      200: '#c7d7f5',
      300: '#a7bfee',
      400: '#8ba9ea',
      500: '#789de5',
      600: '#6389d7',
      700: '#5073c3',
      800: '#405c9f',
      900: '#344b82',
      950: '#212f52',
    },
  },
  primitive: {
    green: {
      50: '#f6fbf4',
      100: '#eaf6e3',
      200: '#d3eac6',
      300: '#b9ddb0',
      400: '#a8d6a0',
      500: '#A2D093',
      600: '#8fbc80',
      700: '#78a56a',
      800: '#5f8455',
      900: '#4b6944',
      950: '#2d3f29',
    },
    red: {
      50: '#fef5f5',
      100: '#fdeaea',
      200: '#f7c9c9',
      300: '#f2a7a7',
      400: '#eb7f7f',
      500: '#E26A6A',
      600: '#cf5757',
      700: '#b64545',
      800: '#913636',
      900: '#742c2c',
      950: '#471919',
    },
  },
});
