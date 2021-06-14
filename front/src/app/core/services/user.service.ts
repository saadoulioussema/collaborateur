import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  
  baseUrl = "http://localhost:8081/";


  findUserByEmailAndUsername(email :string ,username : string): Observable<any> {
    let uri = "findUser/" +username +"/"+email;      
    return this.http.get<any>(this.baseUrl + uri); 
  }

  findUserById(id :number): Observable<any> {
    let uri = "findUser/" +id;      
    return this.http.get<any>(this.baseUrl + uri); 
  }

  getEquipeEnCours(id:number): Observable<any> {
    let uri = "equipeEnCours/"+id;
    return this.http.get<any[]>(this.baseUrl+uri);
  }
}
