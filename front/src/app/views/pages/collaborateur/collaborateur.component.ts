import { Objectif } from './../../../shared/objectif';
import { ObjectifService } from './../../../services/objectif.service';
import { Component,OnInit } from '@angular/core';



@Component({
  selector: 'sc-collaborateur',
  templateUrl: './collaborateur.component.html',
  styleUrls: ['./collaborateur.component.scss']
})
export class CollaborateurComponent implements OnInit  {
	tempList: Array<Objectif> = [];
	objectifs: Objectif[];
	distinction = "4";
	submitted = false;

	constructor(private objectifService: ObjectifService) {
	}

	ngOnInit() {
		this.getAllObjectifs();
	}

	
	change(objectif: Objectif) {
		let flag = false;
		if (this.tempList.length == 0) {
			this.tempList.push(objectif);
		}
		else {
			for (let i = 0; i < this.tempList.length; i++) {
				if (this.tempList[i] == objectif) {
					console.log("objectif found ! saving changes on a temp list  list ");
					flag = true;
					this.tempList[i].autoEvaluation = objectif.autoEvaluation;
					this.tempList[i].commentaire = objectif.commentaire;
				}
				if (flag == false) {
					console.log("objectif not found time to push a new objectif ");
					this.tempList.push(objectif);
				}
			}
		}
	}

	onSubmit() {
		this.submitted = true;
	}

	autoEvaluate() {
		console.log("in auto evaluate method ");
		for (let i = 0; i < this.tempList.length; i++) {
			console.log("saving ...");
			this.objectifService.saveObjectif(this.tempList[i]).subscribe();
		}
	}

	getAllObjectifs() {
		this.objectifService.getObjectifsList().subscribe(data => {
			this.objectifs = data,
				console.log("Objectif List  : ", this.objectifs);
		});
	}

}
