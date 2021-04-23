// Angular
import { Component, Input, OnInit } from '@angular/core';
import { AuthService } from '../../../../../core/services/authentication.service';




@Component({
	selector: 'kt-user-profile',
	templateUrl: './user-profile.component.html',
})
export class UserProfileComponent implements OnInit {
	// Public properties
	fullname:string ;

	@Input() avatar = true;
	@Input() greeting = true;
	@Input() badge: boolean;
	@Input() icon: boolean;

	/**
	 * Component constructor
	 *
	 * @param store: Store<AppState>
	 */
	constructor(private auth: AuthService) {}

	/**
	 * @ Lifecycle sequences => https://angular.io/guide/lifecycle-hooks
	 */

	/**
	 * On init
	 */
	ngOnInit(): void {
		this.fullname=localStorage.getItem("fullname");
	}

	/**
	 * Log out
	 */
	logout() {
		this.auth.logout();
	}
}
