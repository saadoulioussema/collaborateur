import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class DirectionService {



  baseUrl = "http://localhost:8081/direction";
  constructor(private http: HttpClient) { }

    //getDataFromUrl
    getAllDirections(): Observable<any> {
      return this.http.get<any[]>(this.baseUrl);
    }
}
