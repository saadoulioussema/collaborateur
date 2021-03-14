import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';


@Injectable({
  providedIn: 'root'
})
export class ObjectifService {

  constructor(private http: HttpClient) {}

  baseUrl = "http://localhost:8081/";

  getCollaborateurObjectifsForManager(id:number): Observable<any> {
    let uri="objectifsForManager/"+id;
    return this.http.get<any[]>(this.baseUrl+uri);
  }

  getObjectifsList(id:number): Observable<any> {
    let uri="collaborateur/"+id+"/objectifs";
    return this.http.get<any[]>(this.baseUrl+uri);
  }

  saveObjectif(objectif:object): Observable<any> {
    let uri="autoEvaluateObjectif";
    return  this.http.put<any[]>(this.baseUrl+uri,objectif);
  }
}
