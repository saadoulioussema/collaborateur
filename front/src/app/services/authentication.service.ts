import { Injectable } from "@angular/core";
import {HttpClient,HttpErrorResponse} from "@angular/common/http";
import {Observable, throwError } from "rxjs";
import { User } from "../shared/user";
import { map, catchError} from 'rxjs/operators';
import {Router} from '@angular/router';
import { ProcessHttpmsgService } from './process-httpmsg.service';


@Injectable({
  providedIn: "root",
})
export class AuthService {
  constructor(private http: HttpClient,private router: Router,private processHTTPMsgService: ProcessHttpmsgService ) {}

  baseUrl = "http://localhost:8081/";


  
      findUser(email :string ,username : string): Observable<any> {
      let uri = "findUser/" +username +"/"+email;      
      return this.http.get<any>(this.baseUrl + uri); 
    }


    //---------------------------------------------------------------Register---------------------------------------------------------------


  register(json: Object): Observable<any> {
    let uri = "auth/register";
    return this.http.post<User>(this.baseUrl + uri, json).pipe(catchError(this.processHTTPMsgService.handleError));
}


    //------------------------------------------------------------Authentication/Authorization---------------------------------------------------------------


    authenticate(json:Object): Observable<any> {
      let uri="auth/login"
      return this.http.post<any>(this.baseUrl + uri, json).pipe(catchError(this.processHTTPMsgService.handleError));

    }

  

  //---------------------------------------------------------------Utils---------------------------------------------------------------
  get isLoggedIn(): boolean {
    let authToken = localStorage.getItem("jwt");
    return authToken !== null ? true : false;
  }

  logout() {
		localStorage.removeItem('jwt');
		localStorage.removeItem('fullname');
    localStorage.removeItem('managerId');
    localStorage.removeItem('Id');
		this.router.navigateByUrl("/auth");
  }
  


  // Error
  handleError(error: HttpErrorResponse) {
    let msg = "";
    if (error.error instanceof ErrorEvent) {
      // client-side error
      msg = error.error.message;
    } else {
      // server-side error
      msg = `Error Code: ${error.status}\nMessage: ${error.message}`;
    }
    return throwError(msg);
  }
}
