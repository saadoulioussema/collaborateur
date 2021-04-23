import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class NiveauService {

  constructor(private http: HttpClient) { }


  baseUrl = "http://localhost:8081/";


  getNiveauList(): Observable<any> {
    let uri="niveaux";
    return this.http.get<any[]>(this.baseUrl+uri);
  }


}
