import { Objectif } from './../shared/objectif';
import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';


@Injectable({
  providedIn: 'root'
})
export class ObjectifService {

  constructor(private http: HttpClient) {}

  baseUrl = "http://localhost:8081/collaborateur/objectif";
  temp =    "http://localhost:8081/autoEvaluateObjectif";
  objectif:Objectif={id: 4, designation: "Designation4", evaluation: "", commentaire: "cc", autoEvaluation: "Objectif dépassé"};
  //getDataFromUrl
  getObjectifsList(): Observable<any> {
    // this.http.get<any[]>(this.temp);
    return this.http.get<any[]>(this.baseUrl);
  }

  saveObjectif(objectif:object): Observable<any> {
    return  this.http.put<any[]>(this.temp,objectif);
  }
}
