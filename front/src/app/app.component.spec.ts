import { HttpClientModule } from '@angular/common/http';
import {ComponentFixture, TestBed} from '@angular/core/testing';
import { MatToolbarModule } from '@angular/material/toolbar';
import { RouterTestingModule } from '@angular/router/testing';
import { expect } from '@jest/globals';

import { AppComponent } from './app.component';
import {SessionService} from "./services/session.service";
import {LoginComponent} from "./features/auth/components/login/login.component";


describe('AppComponent', () => {
  let mockSessionService: any;
  let app: AppComponent;
  let fixture: ComponentFixture<AppComponent>;
  beforeEach(async () => {

    mockSessionService = {
      $isLogged: jest.fn(),
      logOut: jest.fn()
    }

    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule,
        HttpClientModule,
        MatToolbarModule
      ],
      providers: [
        {provide: SessionService, useValue: mockSessionService}
      ],
      declarations: [
        AppComponent
      ],
    }).compileComponents();
    fixture = TestBed.createComponent(AppComponent);
    app = fixture.componentInstance;
  });

  it('should create the app', () => {
    expect(app).toBeTruthy();
  });

  it('should have $isLogged method', () => {
    app.$isLogged();
    expect(mockSessionService.$isLogged).toHaveBeenCalled();
  });

  it('should have logout method', () => {
    app.logout();
    expect(mockSessionService.logOut).toHaveBeenCalled();
  });
});
