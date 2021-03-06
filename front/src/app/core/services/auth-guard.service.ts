// Angular
import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from '@angular/router';
// NGRX
import { AuthService } from './authentication.service';

@Injectable()
export class AuthGuardService implements CanActivate {
    constructor(private authService: AuthService, private router: Router) { }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) : boolean  {
            if (this.authService.isLoggedIn) {
            return true ;
            }
            else {
            this.router.navigateByUrl('/auth/login');
            return false ;
            }
        }
    }

