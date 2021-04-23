import { UserService } from './../../../../core/services/user.service';
// Angular
import {ChangeDetectorRef,Component, OnDestroy, OnInit, ViewEncapsulation } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
// Translate
import { TranslateService } from '@ngx-translate/core';
// Auth
import { AuthNoticeService} from '../../../../core/services/auth-notice.service';
import { AuthService} from '../../../../core/services/authentication.service';
import { Subject } from 'rxjs';
import { User } from '../../../../shared/user';
import { ConfirmPasswordValidator } from './confirm-password.validator';



@Component({
	selector: 'kt-register',
	templateUrl: './register.component.html',
	encapsulation: ViewEncapsulation.None
})
export class RegisterComponent implements OnInit, OnDestroy {
	user:User = new User;
	errMess: string;
	registerForm: FormGroup;
	loading = false;
	errors: any = [];

	private unsubscribe: Subject<any>; // Read more: => https://brianflove.com/2016/12/11/anguar-2-unsubscribe-observables/

	/**
	 * Component constructor
	 *
	 * @param authNoticeService: AuthNoticeService
	 * @param translate: TranslateService
	 * @param router: Router
	 * @param auth: AuthService
	 * @param fb: FormBuilder
	 * @param cdr
	 */
	constructor(
		private authNoticeService: AuthNoticeService,
		private translate: TranslateService,
		private router: Router,
		private authService: AuthService,
		private userService: UserService,
		private fb: FormBuilder,
		private cdr: ChangeDetectorRef
	) {
		this.unsubscribe = new Subject();
	}

	/*
	 * @ Lifecycle sequences => https://angular.io/guide/lifecycle-hooks
    */

	/**
	 * On init
	 */
	ngOnInit() {
		this.initRegisterForm();
	}

	/*
    * On destroy
    */
	ngOnDestroy(): void {
		this.unsubscribe.next();
		this.unsubscribe.complete();
		this.loading = false;
	}

	/**
	 * Form initalization
	 * Default params, validators
	 */
	initRegisterForm() {
		this.registerForm = this.fb.group({
			fullname: ['', Validators.compose([
				Validators.required,
				Validators.minLength(3),
				Validators.maxLength(100)
			])
			],
			email: ['', Validators.compose([
				Validators.required,
				Validators.email,
				Validators.minLength(3),
				Validators.maxLength(320)
			]),
			],
			username: ['', Validators.compose([
				Validators.required,
				Validators.minLength(3),
				Validators.maxLength(100)
			]),
			],
			password: ['', Validators.compose([
				Validators.required,
				Validators.minLength(3),
				Validators.maxLength(100)
			])
			],
			confirmPassword: ['', Validators.compose([
				Validators.required,
				Validators.minLength(3),
				Validators.maxLength(100)
			])
			],
			agree: [false, Validators.compose([Validators.required])],
			matricule:'',
			dateIntegration:'',

		}, {validator: ConfirmPasswordValidator.MatchPassword});
	}

	/**
	 * Form Submit
	 */

	submit() {
		this.user.clear();
		const controls = this.registerForm.controls;
		// check form
		if (this.registerForm.invalid) {
			Object.keys(controls).forEach(controlName =>
				controls[controlName].markAsTouched()
			);
			return;
		}
		this.loading = true;
		if (!controls.agree.value) {
			// you must agree the terms and condition
			// checkbox cannot work inside mat-form-field https://github.com/angular/material2/issues/7891
			this.authNoticeService.setNotice('You must agree the terms and condition','danger');
			return;
		}

		// Saving User from controls values 
		this.user.fullname = controls.fullname.value;
		this.user.email = controls.email.value.toLowerCase();
		this.user.username = controls.username.value;
		this.user.password = controls.password.value;
		this.user.matricule = controls.matricule.value;
		this.user.dateIntegration = controls.dateIntegration.value;
		// Checking User in database
		this.userService.findUserByEmailAndUsername(this.user.email,this.user.username).subscribe(user => {
			if (user.email) {
				this.authNoticeService.setNotice(this.translate.instant('AUTH.REGISTER.EMAILEXIST'),'danger');
				this.loading = false;
				this.router.navigateByUrl('/auth/login');
			return;
		}
			if (user.username) {
				this.authNoticeService.setNotice(this.translate.instant('AUTH.REGISTER.USERNAMETAKEN'), 'danger');
				this.loading = false;
			}

			if (user.username == null && user.email == null){
				this.authService.register(this.user)
				// must see why ??? 
				// .pipe(takeUntil(this.unsubscribe),finalize(() => {this.loading = false;this.cdr.markForCheck();}))
				.subscribe(user => {
				if (user) {
					console.log("User : ",user);
					this.loading = false;
					this.cdr.markForCheck();
					// pass notice message to the login page
					this.authNoticeService.setNotice(this.translate.instant('AUTH.REGISTER.SUCCESS'), 'success');
					this.router.navigateByUrl('/auth/login');}
				},(errmess) => (this.errMess = <any>errmess));
			}});
	}

	back (){
		this.authNoticeService.setNotice(null);
		this.router.navigateByUrl('/auth/login');
	}
	
	/**
	 * Checking control validation
	 *
	 * @param controlName: string => Equals to formControlName
	 * @param validationType: string => Equals to valitors name
	 */
	isControlHasError(controlName: string, validationType: string): boolean {
		const control = this.registerForm.controls[controlName];
		if (!control) {
			return false;
		}

		const result = control.hasError(validationType) && (control.dirty || control.touched);
		return result;
	}
}
