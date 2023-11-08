import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { SessionService } from 'src/app/services/session.service';
import { MeComponent } from './me.component';
import {of} from "rxjs";
import {Router} from "@angular/router";
import {UserService} from "../../services/user.service";

describe('MeComponent', () => {
  let component: MeComponent;
  let fixture: ComponentFixture<MeComponent>;
  let mockUserService: any;
  let mockSessionService: any;

  beforeEach(async () => {
    mockSessionService = {
      sessionInformation: {
        admin: true,
        id: 1
      },
      logOut: jest.fn()
    }

    await TestBed.configureTestingModule({
      declarations: [MeComponent],
      imports: [
        MatSnackBarModule,
        HttpClientModule,
        MatCardModule,
        MatFormFieldModule,
        MatIconModule,
        MatInputModule,
      ],
      providers: [
        { provide: SessionService, useValue: mockSessionService },
      ],
    })
      .compileComponents();
    mockUserService = TestBed.inject(UserService);
    fixture = TestBed.createComponent(MeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });


  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should back to previous page', () => {
    const back = jest.spyOn(component, 'back');
    const button = fixture.debugElement.nativeElement.querySelector('button');
    button.click();
    expect(back).toHaveBeenCalled();
  });

  it('should get user', () => {
    const mockGetById = jest.spyOn(mockUserService, 'getById').mockReturnValue(of({}));
    component.ngOnInit();
    expect(mockGetById).toHaveBeenCalledWith(mockSessionService.sessionInformation.id.toString());
  });

  it('should delete user', () => {
    const mockDelete = jest.spyOn(mockUserService, 'delete').mockReturnValue(of({}));
    component.delete();
    expect(mockDelete).toHaveBeenCalledWith(mockSessionService.sessionInformation.id.toString());
  });
});

