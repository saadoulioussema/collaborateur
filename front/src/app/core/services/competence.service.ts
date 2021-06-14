import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class CompetenceService {

  constructor(private http: HttpClient) {}

  baseUrl = "http://localhost:8081/";



  // getCompetenceListByUser(userId:number): Observable<any> {
  //   let uri="competences/"+userId;
  //   return this.http.get<any[]>(this.baseUrl+uri);
  // }



  getCompetenceList(): Observable<any> {
    let uri="competences";
    return this.http.get<any[]>(this.baseUrl+uri);
  }

  getCompetence(id:number): Observable<any> {
    let uri="competence/"+id;
    return this.http.get<any[]>(this.baseUrl+uri);
  }
}
