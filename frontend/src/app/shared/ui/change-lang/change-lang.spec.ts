import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChangeLang } from './change-lang';

describe('ChangeLang', () => {
  let component: ChangeLang;
  let fixture: ComponentFixture<ChangeLang>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ChangeLang],
    }).compileComponents();

    fixture = TestBed.createComponent(ChangeLang);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
