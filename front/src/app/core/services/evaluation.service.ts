import { EvaluationPK } from './../../shared/evaluationPK';
import { Evaluation } from './../../shared/evaluation';

import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class EvaluationService {

  constructor(private http: HttpClient) { }

  baseUrl = "http://localhost:8081/";


  newEvaluation(idUser:number,idCompetence:number): Observable<any> {
    let uri="newEvaluation/"+idUser+"/"+idCompetence;
    return this.http.post<any[]>(this.baseUrl+uri,null);
  }
}
