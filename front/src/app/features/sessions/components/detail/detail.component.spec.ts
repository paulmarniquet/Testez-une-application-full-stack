import {HttpClientModule} from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import { RouterTestingModule } from '@angular/router/testing';
import { SessionService } from '../../../../services/session.service';
import { DetailComponent } from './detail.component';
import { SessionApiService } from "../../services/session-api.service";
import {Router} from "@angular/router";
import {of} from "rxjs";
import {SessionsModule} from "../../sessions.module";
import {TeacherService} from "../../../../services/teacher.service";
import {MatIconModule} from "@angular/material/icon";
import {MatCardModule} from "@angular/material/card";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatSelectModule} from "@angular/material/select";

describe('DetailComponent', () => {
  let component: DetailComponent;
  let fixture: ComponentFixture<DetailComponent>;
  let service: SessionService;
  let router: Router;

  const mockSessionService = {
    sessionInformation: {
      admin: true,
      id: 1
    }
  };

  const mockTeacherService = {
    detail: jest.fn((id: string) => {
      return of({ id: id, firstname: 'firstname', lastname: 'lastname', email: 'email', createdAt: new Date(), updatedAt: new Date() });
    })
  }

  const mockApiService = {
    detail: jest.fn((id: string) => {
      return of({ id: id, name: 'name', description: 'description', date: new Date(), teacher_id: 1, users: [], createdAt: new Date(), updatedAt: new Date() });
    }),
    delete: jest.fn((sessionId: string) => {
      return of({});
    }),
    participate: jest.fn((sessionId: string, userId: string) => {
      return of({});
    }),
    unParticipate: jest.fn((sessionId: string, userId: string) => {
      return of({});
    })
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([
          {path: 'sessions', component: SessionsModule}
        ]),
        HttpClientModule,
        MatSnackBarModule,
        ReactiveFormsModule,
        MatCardModule,
        MatIconModule,
        MatFormFieldModule,
        MatInputModule,
        ReactiveFormsModule,
        MatSnackBarModule,
        MatSelectModule,
      ],
      declarations: [DetailComponent],
      providers: [
        {provide: SessionService, useValue: mockSessionService},
        {provide: TeacherService, useValue: mockTeacherService},
        {provide: SessionApiService, useValue: mockApiService}]})
      .compileComponents();
    fixture = TestBed.createComponent(DetailComponent);
    component = fixture.componentInstance;
    service = TestBed.inject(SessionService);
    router = TestBed.inject(Router);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
    expect(mockTeacherService.detail).toHaveBeenCalled();
  });

  it('should back to previous page', () => {
    const spy = jest.spyOn(window.history, 'back');
    component.back();
    expect(spy).toHaveBeenCalled();
  });

  it('should delete session', () => {
    let navigateSpy = jest.spyOn(router, 'navigate');
    component.delete();
    expect(mockApiService.delete).toHaveBeenCalledWith(component.sessionId);
/*
    expect(navigateSpy).toHaveBeenCalledWith(['sessions']);
*/
  });

  it('should participate to session', () => {
    component.participate();
    expect(mockApiService.participate).toHaveBeenCalledWith(component.sessionId, component.userId);
  });

  it('should unparticipate to session', () => {
    component.unParticipate();
    expect(mockApiService.unParticipate).toHaveBeenCalledWith(component.sessionId, component.userId);
  });

});
