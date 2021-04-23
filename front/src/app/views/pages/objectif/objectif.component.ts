
import { Component, OnInit } from '@angular/core';
import { Objectif } from '../../../shared/objectif';
import { ObjectifService } from '../../../core/services/objectif.service';
import { Router } from '@angular/router';
declare const myTest: any;

@Component({
  selector: 'kt-objectif',
  templateUrl: './objectif.component.html',
  styleUrls: ['./objectif.component.scss']
})
export class ObjectifComponent implements OnInit {
  myTest="xoxoxoxoxo";
  title = 'oussema3b3';
  objectifs:Objectif[];
  static designation:string="" ;
  constructor(private objectifService : ObjectifService ,  private router:Router) { }

  ngOnInit() {
  }

  onClick() {
    myTest();
    console.log()
  }
}
