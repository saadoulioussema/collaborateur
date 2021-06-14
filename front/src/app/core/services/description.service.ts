import { Competence } from './../../shared/competence';
import { Description } from './../../shared/description';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class DescriptionService {

  constructor(private http: HttpClient) { }
  baseUrl = "http://localhost:8081/";

  newDescription(desc:Description,idEntretien:number): Observable<any> {
    let uri="newDescription/"+idEntretien;
    return this.http.post<any[]>(this.baseUrl+uri,desc);
  }

  getDescriptionList(): Observable<any> {
    let uri="descriptionList";
    return this.http.get<any[]>(this.baseUrl+uri);
  }

  getDescriptionByCompetence(competence:Competence): Observable<any> {
    let uri="descriptions/"+competence.id;
    return this.http.get<any[]>(this.baseUrl+uri);
  }

}
