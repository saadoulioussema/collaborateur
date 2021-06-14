import { User } from '../../shared/user';
import { Entretien } from './../../shared/entretien';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class EntretienService {

  baseUrl = "http://localhost:8081/";

  constructor(private http: HttpClient) { }


  newEntretien(entretien:Entretien): Observable<any> {
    let uri = "newEntretien";
    return this.http.post<any[]>(this.baseUrl+uri,entretien);
  }


  getCollaborateurByEntretien(entretien:Entretien): Observable<any> {
    let uri = "findCollaborateurByEntretien/"+entretien.id;
    return this.http.get<any[]>(this.baseUrl+uri);
  }

  getEntretienByCollaborateur(collaborateur:User): Observable<any> {
    let uri = "findEntretienByCollaborateur/"+collaborateur.id;
    return this.http.get<any[]>(this.baseUrl+uri);
  }


  getEntretienList(id:number): Observable<any> {
    let uri = "EIPs/"+id;    
    return this.http.get<any[]>(this.baseUrl+uri);
  }

  saveProjectAndFormation(projet:string,formation:string,idEntretien:number): Observable<any> {
    let uri = "saveProjectAndFormation/"+projet+"/"+formation+"/"+idEntretien;   
    return this.http.put<any[]>(this.baseUrl+uri,null);
  }
  closeEntretien(entretien:Entretien){
    let uri = "closeEntretien"  
    return this.http.put<any[]>(this.baseUrl+uri,entretien);
  }
}
