import {HttpClientModule} from '@angular/common/http';
import {ComponentFixture, TestBed} from '@angular/core/testing';
import {ReactiveFormsModule} from '@angular/forms';
import {MatCardModule} from '@angular/material/card';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatIconModule} from '@angular/material/icon';
import {MatInputModule} from '@angular/material/input';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {RouterTestingModule} from '@angular/router/testing';
import {expect} from '@jest/globals';
import {SessionService} from 'src/app/services/session.service';
import {LoginComponent} from './login.component';
import {Router} from "@angular/router";
import {SessionsModule} from "../../../sessions/sessions.module";
import spyOn = jest.spyOn;

describe('LoginComponent', () => {
    let component: LoginComponent;
    let fixture: ComponentFixture<LoginComponent>;
    let router: Router;
    let sessionService: SessionService;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [LoginComponent],
            providers: [SessionService],
            imports: [
                RouterTestingModule.withRoutes([
                    {path: 'sessions', component: SessionsModule}
                ]),
                BrowserAnimationsModule,
                HttpClientModule,
                MatCardModule,
                MatIconModule,
                MatFormFieldModule,
                MatInputModule,
                ReactiveFormsModule]
        })
            .compileComponents();
        router = TestBed.inject(Router);
        sessionService = TestBed.inject(SessionService);
        fixture = TestBed.createComponent(LoginComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });

    it('should have a form', () => {
        expect(component.form).toBeTruthy();
    });

    it('should login with correct credentials', () => {
        const loginRequest = {email: 'yoga@studio.com', password: 'test!1234'};
        component.form.setValue(loginRequest);
        const submitting = spyOn(component, 'submit');
        const navigateSpy = spyOn(router, 'navigate');

        component.submit();
        expect(submitting).toHaveBeenCalled();
        expect(component.onError).toBe(false);
        expect(navigateSpy).toHaveBeenCalledWith(['/sessions']);
    });

    /*    it('should not login with incorrect credentials', () => {
            const loginRequest = {email: 'incorrect@example.com', password: '##'};
            component.form.setValue(loginRequest);
            component.submit();
            expect(component.onError).toBe(true);
        });*/
});
