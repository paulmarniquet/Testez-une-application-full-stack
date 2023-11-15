// session-api.service.spec.ts
import {HttpClient, HttpClientModule} from '@angular/common/http';
import {TestBed} from '@angular/core/testing';
import {expect} from '@jest/globals';
import {SessionApiService} from './session-api.service';
import {Session} from "../interfaces/session.interface";
import {HttpClientTestingModule, HttpTestingController} from "@angular/common/http/testing";

describe('SessionApiService', () => {
  let service: SessionApiService;
  let httpClient: HttpClient;
  let httpTestingController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientModule,
        HttpClientTestingModule
      ],
      providers: [
        SessionApiService
      ]
    });
    service = TestBed.inject(SessionApiService);
    httpClient = TestBed.inject(HttpClient);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should get all sessions', () => {
    service.all().subscribe((sessions) => {
      expect(sessions).toBeTruthy();
      const req = httpTestingController.expectOne('api/session');
      expect(req.request.method).toEqual('GET');
      httpTestingController.verify();
    });
  });

  it('should get one specific session', () => {
    service.detail('1').subscribe((session) => {
      expect(session).toBeTruthy();
      const req = httpTestingController.expectOne('api/session/1');
      expect(req.request.method).toEqual('GET');
      httpTestingController.verify();
    });
  });

  it('should create a session', () => {
    const newSession: Session = {
      date: new Date(),
      description: "",
      name: "",
      teacher_id: 0,
      users: []
    };
    service.create(newSession).subscribe((session) => {
      expect(session).toBeTruthy();
      const req = httpTestingController.expectOne('api/session');
      expect(req.request.method).toEqual('POST');
      httpTestingController.verify();
    });
  });

  it('should update a session', () => {
    const updatedSession: Session = {
      date: new Date,
      description: "",
      name: "",
      teacher_id: 0,
      users: []
    };
    service.update('1', updatedSession).subscribe((session) => {
      expect(session).toBeTruthy();
      const req = httpTestingController.expectOne('api/session/1');
      expect(req.request.method).toEqual('PUT');
      httpTestingController.verify();
    });
  });

  it('should delete one specific session', () => {
    service.delete('1').subscribe(() => {
      const req = httpTestingController.expectOne('api/session/1');
      expect(req.request.method).toEqual('DELETE');
      req.flush({});
      httpTestingController.verify();
    });
  });

  it('should participate to one specific session', () => {
    service.participate('1', '1').subscribe(() => {
      const req = httpTestingController.expectOne('api/session/1/participate/1');
      expect(req.request.method).toEqual('POST');
      req.flush({});
      httpTestingController.verify();
    });
  });

  it('should unparticipate to one specific session', () => {
    service.unParticipate('1', '1').subscribe(() => {
      const req = httpTestingController.expectOne('api/session/1/participate/1');
      expect(req.request.method).toEqual('DELETE');
      req.flush({});
      httpTestingController.verify();
    });
  });
});
