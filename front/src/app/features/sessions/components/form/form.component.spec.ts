import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import {  ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import {BrowserAnimationsModule, NoopAnimationsModule} from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';
import { expect } from '@jest/globals';
import { SessionService } from 'src/app/services/session.service';
import { SessionApiService } from '../../services/session-api.service';

import { FormComponent } from './form.component';
import {Router} from "@angular/router";
import {of} from "rxjs";

describe('FormComponent', () => {
  let component: FormComponent;
  let fixture: ComponentFixture<FormComponent>;
  let router: Router;

  const mockSessionService = {
    sessionInformation: {
      admin: true
    }
  }

  const mockApiService = {
    detail: jest.fn((id: string) => {
      return of({ id: id, name: 'name', description: 'description', date: new Date(), teacher_id: 1, users: [], createdAt: new Date(), updatedAt: new Date() });
    }),
    delete: jest.fn((sessionId: string) => {
      return of({});
    }),
    create: jest.fn((session: any) => {
      return of({});
    }),
    update: jest.fn((id: string, session: any) => {
      return of({});
    })
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([
          {path: 'sessions', component: FormComponent}
        ]),
        HttpClientModule,
        MatCardModule,
        MatIconModule,
        MatFormFieldModule,
        MatInputModule,
        ReactiveFormsModule,
        MatSnackBarModule,
        MatSelectModule,
        NoopAnimationsModule
      ],
      providers: [
        { provide: SessionService, useValue: mockSessionService },
        { provide: SessionApiService, useValue: mockApiService }
  ],
      declarations: [FormComponent]
    })
      .compileComponents();

    fixture = TestBed.createComponent(FormComponent);
    component = fixture.componentInstance;
    router = TestBed.inject(Router);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should submit form if it is not in update', () => {
    component.onUpdate = false;
    component.submit();
    expect(mockApiService.create).toHaveBeenCalled();
  });

  it('should submit form if it is in update', () => {
    component.onUpdate = true;
    component.submit();
    expect(mockApiService.update).toHaveBeenCalled();
  })

  it('should init form for update', () => {
    const spyNavigate = jest.spyOn(router, 'navigate');
    jest.spyOn(router, 'url', 'get').mockReturnValue('/update/1');
    component.ngOnInit();
    expect(component.onUpdate).toBeTruthy();
    expect(mockApiService.detail).toHaveBeenCalled();
/*
    expect(spyNavigate).not.toHaveBeenCalled();
*/
  });

  it('should navigate if admin is false', () => {
    const spyNavigate = jest.spyOn(router, 'navigate');
    mockSessionService.sessionInformation!.admin = false;
    component.ngOnInit();
    expect(spyNavigate).toHaveBeenCalledWith(['/sessions']);
  });


});
