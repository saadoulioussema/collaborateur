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

  updateDescriptionLevel(desc:Description,newLevel:number): Observable<any> {
    console.log(desc);
    let uri="updateDescriptionLevel/"+newLevel;
    return this.http.put<any[]>(this.baseUrl+uri,desc);
  }

  getDescriptionList(): Observable<any> {
    let uri="descriptionList";
    return this.http.get<any[]>(this.baseUrl+uri);
  }

  getDescription(desc:Description): Observable<any> {
    let idCompetence = desc.descriptionPK.idCompetence;
    let idNiveau = desc.descriptionPK.idNiveau;
    let uri="description/"+idCompetence+"/"+idNiveau
    return this.http.get<any[]>(this.baseUrl+uri);
  }
}
