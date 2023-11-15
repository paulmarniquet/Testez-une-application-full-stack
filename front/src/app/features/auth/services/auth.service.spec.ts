import {TestBed} from '@angular/core/testing';
import {expect} from '@jest/globals';
import {AuthService} from './auth.service';
import {HttpClient, HttpClientModule} from "@angular/common/http";
import {HttpClientTestingModule, HttpTestingController} from "@angular/common/http/testing";

describe('AuthService', () => {
  let service: AuthService;
  let httpClient: HttpClient;
  let httpTestingController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientModule,
        HttpClientTestingModule
      ]
    });
    service = TestBed.inject(AuthService);
    httpClient = TestBed.inject(HttpClient);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should register a user', () => {
    const user = {
      email: 'email@gmail.com',
      firstName: 'firstName',
      lastName: 'lastName',
      password: 'password'
    }
    service.register(user).subscribe((user) => {
      expect(user).toBeTruthy();
      const req = httpTestingController.expectOne('api/auth/register');
      expect(req.request.method).toEqual('POST');
      req.flush({});
      httpTestingController.verify();
    });
  });

  it('should log in', () => {
    const user = {
      email: 'email@gmail.com',
      password: 'password'
    }
    service.login(user).subscribe((user) => {
      const req = httpTestingController.expectOne('api/auth/login');
      expect(req.request.method).toEqual('POST');
      req.flush({});
      httpTestingController.verify();
    });
  });
});
