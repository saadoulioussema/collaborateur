
import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Objectif } from './../../shared/objectif';



@Injectable({
  providedIn: 'root'
})
export class ObjectifService {

  constructor(private http: HttpClient) {}

  baseUrl = "http://localhost:8081/";


  getObjectifList(id:number): Observable<any> {
    let uri="entretien/"+id+"/objectifs";
    return this.http.get<any[]>(this.baseUrl+uri);
  }

  saveObjectif(objectif:Objectif): Observable<any> {
    let uri="autoEvaluateObjectif";
    return  this.http.put<any[]>(this.baseUrl+uri,objectif);
  }

  evaluate(objectif:Objectif): Observable<any> {
    let uri="evaluateObjectif";
    return  this.http.put<any[]>(this.baseUrl+uri,objectif);
  }

  saveNewObjectifs(objectifs:Objectif[],newEntretienId:number,idEntretien:number): Observable<any> {
    let uri="newObjectifs/"+newEntretienId+"/"+idEntretien;
    return  this.http.post<any[]>(this.baseUrl+uri,objectifs);
  }

  saveOtherNewObjectifs(objectifs:Objectif[],idUser:number): Observable<any> {
    let uri="newOtherObjectifs/"+idUser;  
    return  this.http.put<any[]>(this.baseUrl+uri,objectifs);
  }

  delete(idUser:number,designation:string): Observable<any> {
    let uri="deleteNewObjectif/"+idUser+"/"+designation;
    return  this.http.delete<any[]>(this.baseUrl+uri);
  }
}

