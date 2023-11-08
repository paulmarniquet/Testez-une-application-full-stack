import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { expect } from '@jest/globals';

import { RegisterComponent } from './register.component';
import {AuthService} from "../../services/auth.service";
import {of, throwError} from "rxjs";

describe('RegisterComponent', () => {
  let component: RegisterComponent;
  let fixture: ComponentFixture<RegisterComponent>;
  let mockAuthService: any;

  beforeEach(async () => {

    mockAuthService = {
      register: jest.fn()
    }

    await TestBed.configureTestingModule({
      declarations: [RegisterComponent,
      ],
      providers: [
        {provide: AuthService, useValue: mockAuthService},
        ],
      imports: [
        BrowserAnimationsModule,
        HttpClientModule,
        ReactiveFormsModule,
        MatCardModule,
        MatFormFieldModule,
        MatIconModule,
        MatInputModule
      ]
    })
      .compileComponents();

    fixture = TestBed.createComponent(RegisterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should have a form with 4 inputs', () => {
    expect(component.form.contains('email')).toBeTruthy();
    expect(component.form.contains('firstName')).toBeTruthy();
    expect(component.form.contains('lastName')).toBeTruthy();
    expect(component.form.contains('password')).toBeTruthy();
  });

  it("should submit valid form", () => {
    const registerForm = {email: "register@test.com", firstName: "Regis", lastName: "Ter", password: "form"};
    mockAuthService.register = jest.fn(() => of(registerForm));
    component.form.patchValue(registerForm);
    component.submit();
    expect(component.form.valid).toBeTruthy();
    expect(mockAuthService.register).toHaveBeenCalledWith(registerForm);
  });

  it("should not submit invalid form", () => {
    const registerForm = {email: "", firstName: "", lastName: "", password: ""};
    mockAuthService.register = jest.fn().mockReturnValue(throwError('Invalid form'));
    component.form.patchValue(registerForm);
    component.submit();
    expect(component.form.valid).toBeFalsy();
  });
});
