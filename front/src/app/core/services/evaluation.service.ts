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


  // newEvaluation(idUser:number,idCompetence:number): Observable<any> {
  //   let uri="newEvaluation/"+idUser+"/"+idCompetence;
  //   return this.http.post<any[]>(this.baseUrl+uri,null);
  // }

  newEvaluation(evaluation: Evaluation): Observable<any> {
    let uri = "newEvaluation";
    return this.http.post<any[]>(this.baseUrl + uri, evaluation);
  }

  updateEvaluationLevel(evaluation: Evaluation): Observable<any> {
    let uri = "updateEvaluationLevel";
    return this.http.put<any[]>(this.baseUrl + uri, evaluation);
  }

  getEvaluation(evaluation: Evaluation): Observable<any> {
    let idUser = evaluation.evaluationPK.idUser;
    let idCompetence = evaluation.evaluationPK.idCompetence;
    let uri = "evaluation/" + idUser + "/" + idCompetence
    return this.http.get<any[]>(this.baseUrl + uri);
  }
}
