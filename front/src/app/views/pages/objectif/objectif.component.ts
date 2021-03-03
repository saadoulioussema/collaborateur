
import { Component, OnInit } from '@angular/core';
import { Objectif } from '../../../shared/objectif';
import { ObjectifService } from '../../../services/objectif.service';
import { Router } from '@angular/router';


@Component({
  selector: 'kt-objectif',
  templateUrl: './objectif.component.html',
  styleUrls: ['./objectif.component.scss']
})
export class ObjectifComponent implements OnInit {
  objectifs:Objectif[];
  static designation:string="" ;
  constructor(private objectifService : ObjectifService ,  private router:Router) { }

  ngOnInit() {
  this.getAllObjectifs();
  }

  copy(designationC: string){

    console.log("designationC : ",designationC);
    console.log("designation : ",ObjectifComponent.designation);
    ObjectifComponent.designation = designationC;
    this.router.navigate(["/wizard/wizard-3"]);
  }


  getAllObjectifs(){
    this.objectifService.getObjectifsList().subscribe(data=>{
    this.objectifs=data,
    console.log( "lista taa les objectifs : ",this.objectifs);
  });
  }

  

}
