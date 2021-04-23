import { ObjectifService } from '../../../core/services/objectif.service';
import { Objectif } from './../../../shared/objectif';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'kt-my-page',
  templateUrl: './my-page.component.html',
  styleUrls: ['./my-page.component.scss']
})
export class MyPageComponent implements OnInit {



  // lista:Objectif[]=[{id:1,designation:"one",evaluation:"bbbb",commentaire:"commentaire1",autoEvaluation:"ddddd"},{id:2,designation:"two",evaluation:"bbffffbb",commentaire:"commentaire2",autoEvaluation:"dfffdddd"}]
  lista:Objectif[];
  constructor(private objectifService: ObjectifService) { }

  ngOnInit() {
    let id = localStorage.getItem("Id");
		this.getAllObjectifs(parseInt(id));
  }

  fonction(obj :Objectif) {
    // this.objectif=obj;
    // console.log("COMMENTAIRE====>",this.objectif.commentaire);
    //  console.log("DESIGNAITON ====>",this.objectif.designation);
  }

  getAllObjectifs(id: number) {
		this.objectifService.getObjectifList(id).subscribe(data => {
			this.lista = data,
				console.log("Lista : ", this.lista);
		});
	}

}
