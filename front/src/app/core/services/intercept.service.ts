// Angular
import { Injectable } from '@angular/core';
import {HttpInterceptor, HttpHandler, HttpRequest} from '@angular/common/http';



/**
 * More information there => https://medium.com/@MetonymyQT/angular-http-interceptors-what-are-they-and-how-to-use-them-52e060321088
 */
@Injectable()
export class InterceptService implements HttpInterceptor {
	// intercept request and add token
	// intercept(request: HttpRequest<any>,next: HttpHandler): Observable<HttpEvent<any>> {
	// 	// tslint:disable-next-line:no-debugger
	// 	// modify request
	// 	// request = request.clone({
	// 	// 	setHeaders: {
	// 	// 		Authorization: `Bearer ${localStorage.getItem('accessToken')}`
	// 	// 	}
	// 	// });
	// 	// console.log('----request----');
	// 	// console.log(request);
	// 	// console.log('--- end of request---');


		
	// 	return next.handle(request).pipe(
	// 		tap(
	// 			event => {
	// 				//  if (event instanceof HttpResponse) {
	// 				// 	console.log('all looks good');
	// 				// 	// http response status code
	// 				// 	console.log(event.status);
	// 				// }
	// 			},
	// 			error => {
	// 				// http response status code
	// 				// console.log('----response----');
	// 				// console.error('status code:');
	// 				// tslint:disable-next-line:no-debugger
	// 					//Default :
	// 				console.error(error.status);
	// 				console.error(error.message);
	// 				// console.log('--- end of response---');
	// 		}))};



	constructor(){}
	intercept(req: HttpRequest<any>, next: HttpHandler) {

		if (localStorage.getItem('fullname') && localStorage.getItem('jwt')) {
		  const headerss = {
			'Content-type' : 'application/json',
			'Accept': 'application/json',
			'Authorization' : localStorage.getItem('jwt')
		  }
		  req = req.clone({
			setHeaders: headerss
		  })
		}
		//Default :
		return next.handle(req);
		// return next.handle(req).pipe(catchError(err => {
		// 	console.log("Error in da Service /////////////// : ",err.status);
		// 	  if (err.status === 401) {
		// 		  // auto logout if 401 response returned from api
		// 		  this.authNoticeService.setNotice(this.translate.instant('AUTH.VALIDATION.INVALID_LOGIN'), 'danger');
		// 		//   this.authService.logout();
		// 	  }
	  
		// 	  const error = err.error.message || err.statusText;
		// 	  return throwError(error);
		//   }))
	  }
	}
