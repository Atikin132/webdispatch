import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LoginEditComponent } from './login-edit.component';

describe('LoginEditComponent', () => {
  let component: LoginEditComponent;
  let fixture: ComponentFixture<LoginEditComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LoginEditComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(LoginEditComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
