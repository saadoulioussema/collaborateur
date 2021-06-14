import { ManagerComponent } from './../manager/manager.component';
import { EvaluationComponent } from './../evaluation/evaluation.component';
import { AuthNotice } from './../../../core/services/auth-notice.interface';
import { Subscription } from 'rxjs';
import { User } from './../../../shared/user';
import { EntretienService } from './../../../core/services/entretien.service';
import { Entretien } from './../../../shared/entretien';
import { ObjectifService } from './../../../core/services/objectif.service';
import { EipsComponent } from './../eips/eips.component';
import { AuthNoticeService } from './../../../core/services/auth-notice.service';
import { Objectif } from './../../../shared/objectif';
import { Component, Input, OnInit, Output } from '@angular/core';

const status2 = ['FIXATION_OBJECTIFS', 'EVALUATION_COMPETENCES', 'PROJET_PROFESSIONEL', 'CLOTURE'];

@Component({
	selector: 'app-fixing-objectifs',
	templateUrl: './fixing-objectifs.component.html',
	styleUrls: ['./fixing-objectifs.component.scss']
})
export class FixingObjectifsComponent implements OnInit {

	@Input() collaborateur: User = new User();
	// Notice Setup
	@Output() type: any;
	@Output() message: any = '';
	// Private properties
	private subscriptions: Subscription[] = [];

	//Les nouveaux objectifs qui seront fixés par le manager 
	newObjectifList: Objectif[] = [];
	//Index of the new objectif List 
	index = 0;
	//Fixing new Objectifs 
	newObjectif: Objectif = new Objectif();
	obj: Objectif = new Objectif();
	//EMPTY NEW OBJECTIF DETECTED
	objectifFlag = false;
	assignedObjectifs: Objectif[] = [];
	autoEvaluation = false;
	newObjectifFixed = false;

	entretien: Entretien;


	constructor(private authNoticeService: AuthNoticeService,
		private objectifService: ObjectifService,
		private entretienService: EntretienService) { }

	ngOnInit(): void {

		this.initiateField();
		if (EipsComponent.entretien && status2.includes(EipsComponent.entretien.status))
			this.objectifService.getAssignedObjectifs(EipsComponent.entretien.user.id).subscribe(data => this.assignedObjectifs = data);
	}


	initiateField() {
		// Step 2 : Prépaer un champ de saisie initial pour le manager 
		this.obj = new Objectif();
		this.newObjectifList=[];
		this.newObjectifList.push(this.obj);
		this.index++;
	}

	addNew(objectif: Objectif) {
		this.newObjectif = objectif;
		this.objectifFlag = false;
	}

	showOneMore() {
		// L'utlisateur n'a pas rempli textarea et veut un autre :
		if (this.newObjectif.designation == "") {
			this.authNoticeService.setNotice("La désignation de l'objectif est vide  !", 'danger');
			setTimeout(() => { this.authNoticeService.setNotice(null, null); }, 6000);
		}
		else {
			// L'utlisateur a rempli textarea et veut un autre :
			this.obj = new Objectif();
			this.newObjectifList.push(this.obj);
			this.index++;
			this.objectifFlag = true;
		}


	}
	showOneLess() {
		this.newObjectifList.splice(this.index - 1, 1);
		this.index--;
	}

	fixingNewObjectifs() {
		if (this.collaborateur) {
			this.entretienService.getEntretienByCollaborateur(this.collaborateur).subscribe(data => {
				this.entretien = data


				//Manager can't save an empty new objectif
				for (let i = 0; i < this.newObjectifList.length; i++) {
					if (this.newObjectifList[i].designation == "")
						this.objectifFlag = true;
				}

				if (!this.objectifFlag) {
					if (this.entretien.status == "FIXATION_OBJECTIFS") {
						//Saving ohter new Objectifs
						console.log("saving other new objectifs...");
						this.objectifService.saveOtherNewObjectifs(this.newObjectifList, this.collaborateur.id).subscribe();
						// this.objectifService.saveNewObjectif(this.newObjectifList,0,EipsComponent.entretien.id).subscribe();
						console.log("saved");
						for (let i = 0; i < this.newObjectifList.length; i++) {
							this.assignedObjectifs.push(this.newObjectifList[i]);
						}
						this.initiateField();
						this.authNoticeService.setNotice("Votre modification a été sauvgarder avec success !", 'success');
						setTimeout(() => { this.authNoticeService.setNotice(null, null); }, 4000);
					}
					else {
						//Saving new Objectifs
						let entretien: Entretien = new Entretien();
						this.entretienService.newEntretien(entretien).subscribe(entretien => {
							console.log("saving new objectifs...");
							this.objectifService.saveNewObjectifs(this.newObjectifList, entretien.id, EipsComponent.entretien.id).subscribe();
							// EipsComponent.entretien.status = "FIXATION_OBJECTIFS";
							console.log("saved");
							this.assignedObjectifs = this.newObjectifList;
							this.initiateField();
							this.authNoticeService.setNotice("Votre modification a été sauvgarder avec success !", 'success');
							setTimeout(() => { this.authNoticeService.setNotice(null, null); }, 4000);
						});
					}
				}
			});
		}

	}

	deleteSavedObjectif(savedObj: Objectif) {
		// marche seulement si on supprime le premier element
		// let index = this.assignedObjectifs.findIndex(obj => obj.id === savedObj.id);
		// this.assignedObjectifs.splice(index, 1);
		this.objectifService.delete(this.collaborateur.id, savedObj.designation).subscribe(data=>this.assignedObjectifs=data);

	}


	save() {
		// Notice Setup
		this.subscriptions.push(this.authNoticeService.onNoticeChanged$.subscribe(
			(notice: AuthNotice) => {
				notice = Object.assign({}, { message: '', type: '' }, notice);
				this.message = notice.message;
				this.type = notice.type;
			}
		));
		//1- Evaluations des objectifs 
		this.fixingNewObjectifs();
	}

}
