import { UserService } from './../../../../core/services/user.service';
// Angular
import { ChangeDetectorRef, Component, OnDestroy, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
// RxJS
import { Observable, Subject } from 'rxjs';
// Translate
import { TranslateService } from '@ngx-translate/core';
// Auth
import { AuthNoticeService } from '../../../../core/services/auth-notice.service';
import { User } from '../../../../shared/user';
import { AuthService } from '../../../../core/services/authentication.service';



@Component({
	selector: 'kt-login',
	templateUrl: './login.component.html',
	encapsulation: ViewEncapsulation.None
})
export class LoginComponent implements OnInit, OnDestroy {
	// Public params
	user: User = new User;
	loginForm: FormGroup;
	loading = false;
	isLoggedIn$: Observable<boolean>;
	errorss: any = [];
	errMess: string;

	private unsubscribe: Subject<any>;

	private returnUrl: any;

	// Read more: => https://brianflove.com/2016/12/11/anguar-2-unsubscribe-observables/

	/**
	 * Component constructor
	 *
	 * @param router: Router
	 * @param auth: AuthService
	 * @param authNoticeService: AuthNoticeService
	 * @param translate: TranslateService
	 * @param store: Store<AppState>
	 * @param fb: FormBuilder
	 * @param cdr
	 * @param route
	 */
	constructor(
		private router: Router,
		private authService: AuthService,
		private userService: UserService,
		private authNoticeService: AuthNoticeService,
		private translate: TranslateService,
		private fb: FormBuilder,
		private cdr: ChangeDetectorRef,
		private route: ActivatedRoute
	) {
		this.unsubscribe = new Subject();
	}

	/**
	 * @ Lifecycle sequences => https://angular.io/guide/lifecycle-hooks
	 */

	/**
	 * On init
	 */
	ngOnInit(): void {
		this.initLoginForm();

		// redirect back to the returnUrl before login
		this.route.queryParams.subscribe(params => {
			this.returnUrl = params.returnUrl || '/';
		});

		if (this.authService.isLoggedIn) {
			this.router.navigateByUrl("/dashboard")
		}

	}

	/**
	 * On destroy
	 */
	ngOnDestroy(): void {
		this.authNoticeService.setNotice(null);
		this.unsubscribe.next();
		this.unsubscribe.complete();
		this.loading = false;
	}

	/**
	 * Form initalization
	 * Default params, validators
	 */
	initLoginForm() {
		//  message to show whe the form is initiated 

		this.authNoticeService.setNotice(null, null);

		this.loginForm = this.fb.group({
			email: ['', Validators.compose([
				Validators.required,
				Validators.email,
				Validators.minLength(3),
				Validators.maxLength(320) // https://stackoverflow.com/questions/386294/what-is-the-maximum-length-of-a-valid-email-address
			])
			],
			password: ['', Validators.compose([
				Validators.required,
				Validators.minLength(3),
				Validators.maxLength(100)
			])
			]
		});
	}

	/**
	 * Form Submit
	 */

	submit() {
		const controls = this.loginForm.controls;
		/** check form */
		if (this.loginForm.invalid) {
			Object.keys(controls).forEach(controlName =>
				controls[controlName].markAsTouched()
			);
			return;
		}
		this.loading = true;
		this.user.email = controls.email.value.toLowerCase(),
		this.user.password = controls.password.value

		
		// get the user details if needed  (optional !) 
		this.userService.findUserByEmailAndUsername(this.user.email, " ").subscribe(user => {
			this.authService.authenticate(this.user).subscribe(res => {
				if (res) {
					this.loading = false;
					// Dont know why must see ! 
					this.cdr.markForCheck(); 
					// console.log("recieved token : ",res.token);
					//LocalStorage
					localStorage.setItem("jwt", res.token);
					localStorage.setItem("fullname", user.fullname);
					// this.router.navigateByUrl(this.returnUrl); // Main page
					if (user.fonctionId == 2) {
						localStorage.setItem("managerId", user.managerId);
						this.router.navigateByUrl("/eips");
					}
					else {
						localStorage.setItem("Id", user.id);
						this.router.navigateByUrl("/autoEvaluate");
					}

				}
			}, (errmess) => {
				this.errMess = <any>errmess;
				this.authNoticeService.setNotice(this.translate.instant('AUTH.VALIDATION.INVALID_LOGIN'), 'danger');
				this.loading = false;
			});
		});

	}

	/**
	 * Checking control validation
	 *
	 * @param controlName: string => Equals to formControlName
	 * @param validationType: string => Equals to valitors name
	 */
	isControlHasError(controlName: string, validationType: string): boolean {
		const control = this.loginForm.controls[controlName];
		if (!control) {
			return false;
		}

		const result = control.hasError(validationType) && (control.dirty || control.touched);
		return result;
	}
}
