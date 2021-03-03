// import { Injectable } from '@angular/core';
// import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor } from '@angular/common/http';
// import { Observable, throwError } from 'rxjs';
// import { catchError } from 'rxjs/operators';

// import { AuthService} from './authentication.service';
// // Translate
// import { TranslateService } from '@ngx-translate/core';
// // Auth
// import { AuthNoticeService} from './auth-notice.service';

// @Injectable({
//   providedIn: 'root'
// })
// export class ErrorInterceptorService implements HttpInterceptor {

//   constructor(private authService: AuthService,		private authNoticeService: AuthNoticeService,		private translate: TranslateService) { }

//   intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
//     return next.handle(request).pipe(catchError(err => {
//       console.log("Error in da Service /////////////// : ",err.status);
//         if (err.status === 401) {
//             // auto logout if 401 response returned from api
//             this.authNoticeService.setNotice(this.translate.instant('AUTH.VALIDATION.INVALID_LOGIN'), 'danger');
//             this.authService.logout();
//         }

//         const error = err.error.message || err.statusText;
//         return throwError(error);
//     }))
// }
// }
