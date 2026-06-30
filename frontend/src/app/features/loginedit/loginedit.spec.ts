import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Loginedit } from './loginedit';

describe('Loginedit', () => {
  let component: Loginedit;
  let fixture: ComponentFixture<Loginedit>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Loginedit],
    }).compileComponents();

    fixture = TestBed.createComponent(Loginedit);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
