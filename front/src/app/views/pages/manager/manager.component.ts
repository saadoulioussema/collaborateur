import { Subscription } from 'rxjs';
import { Router } from '@angular/router';
import { Component, OnInit, Output, ViewChild, ElementRef } from '@angular/core';

import { AuthNotice } from './../../../services/auth-notice.interface';
import { Objectif } from './../../../shared/objectif';
import { User } from './../../../shared/user';
import { Entretien } from './../../../shared/entretien';

//Service 
import { EntretienService } from './../../../services/entretien.service';
import { ObjectifService } from './../../../services/objectif.service';
import { AuthNoticeService } from './../../../services/auth-notice.service';
import { TranslateService } from '@ngx-translate/core';


@Component({
	selector: 'sc-manager',
	templateUrl: './manager.component.html',
	styleUrls: ['./manager.component.scss']
})
export class ManagerComponent implements OnInit {
	// Notice Setup
	@Output() type: any;
	@Output() message: any = '';
	// Private properties
	private subscriptions: Subscription[] = [];

	@ViewChild('wizard', { static: true }) el: ElementRef;
	// Manager properties
	entretien: Entretien;
	collaborateur: User = null;
	managerId: string;
	EIPs: Entretien[];
	//Les objectifs d'un collaborateur
	objectifs: Objectif[];
	//Les objectifs qui seront validés par le manager 
	objectifList: Array<Objectif> = [];
	//Les nouveaux objectifs qui seront fixés par le manager 
	newObjectifList: Array<Objectif> = [];
	//Index of the new objectif List 
	index = 0;
	//Fixing new Objectifs 
	newObjectif: Objectif = new Objectif();
	obj: Objectif = new Objectif();
	//EMPTY NEW OBJECTIF DETECTED
	objectifFlag = false;
	stepNumber = 1;
	savedObjectifs: Array<Objectif> = [];
	newObjectifFixed=false;


	constructor(private entretienService: EntretienService, private objectifService: ObjectifService, private router: Router, private authNoticeService: AuthNoticeService,
		private translate: TranslateService) { }



	ngOnInit() {
		// Notice Setup
		this.subscriptions.push(this.authNoticeService.onNoticeChanged$.subscribe(
			(notice: AuthNotice) => {
				notice = Object.assign({}, { message: '', type: '' }, notice);
				this.message = notice.message;
				this.type = notice.type;
			}
		));
		this.authNoticeService.setNotice("Vous pouvez cliquer sur un collaborateur pour voir plus de details !", 'info');
		this.managerId = localStorage.getItem("managerId");

		// Step 1 : Evaluation : prépaer la liste des EIPs pour le manager 
		let id = parseInt(this.managerId)
		this.getEIPs(id);
		// Step 2 : Prépaer un champ de saisie initial pour le manager 
		this.obj = new Objectif();
		this.newObjectifList.push(this.obj);
		this.index++;


	}

	ngAfterViewInit(): void {
		// Initialize form wizard
		const wizard = new KTWizard(this.el.nativeElement, {
			startStep: 1
		});

		// Validation before going to next page
		wizard.on('beforeNext', (wizardObj) => {
			// validate the form and use below function to stop the wizard's step
			if (!this.collaborateur || this.stepNumber == 1) {
				wizardObj.stop();
			}
		});

		wizard.on('afterPrev', (wizardObj) => {
			if (this.stepNumber == 1) 
			{
			// Evaluation : préparer la liste des EIPs pour le manager 
			let id = parseInt(this.managerId)
			this.getEIPs(id);
			//Initialiser la liste des objectifs au cas le manager retourne vers evaluate
			//Forcer la longeur de la liste à zéro 
			this.objectifList = [];
			//Initialiser la liste des newObjectifs au cas le manager retourne vers evaluation
			this.newObjectifList = [];
			this.obj = new Objectif();
			this.newObjectifList.push(this.obj);
			this.index++;
			}
		});

		

		// Change event
		wizard.on('change', (wizardObj) => {
			setTimeout(() => {
				KTUtil.scrollTop();
			}, 500);
			console.log("Step NUMBER", this.stepNumber);
			if (this.stepNumber == 2) {
				wizardObj.goTo(2);
			}

		});


	}



	getCollaborateur(entretien: Entretien) {
		this.entretienService.getCollaborateurByEntretien(entretien).subscribe(data => {
			this.collaborateur = data,
				this.findHisObjectif(this.collaborateur.id),
				this.entretien = entretien;
			if (this.collaborateur)
				this.authNoticeService.setNotice(null, null);
		});
	}

	findHisObjectif(id: number) {
		this.objectifService.getCollaborateurObjectifsForManager(id).subscribe(data => {
			this.objectifs = data
		});
	}




	getEIPs(id: number) {
		this.entretienService.getEntretienList(id).subscribe(data => {
			this.EIPs = data;
		});
	}

	change(objectif: Objectif) {
		let flag = false;
		if (this.objectifList.length == 0) {
			console.log(" empty list  ! saving changes ");
			this.objectifList.push(objectif);
		}
		else {
			for (let i = 0; i < this.objectifList.length; i++) {
				if (this.objectifList[i] == objectif) {
					console.log("objectif found ! saving changes");
					flag = true;
					this.objectifList[i].evaluation = objectif.autoEvaluation;
					this.objectifList[i].commentaire = objectif.commentaire;
				}
				if (flag == false) {
					console.log("objectif not found time to push a new objectif ");
					this.objectifList.push(objectif);
				}
			}
		}
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


	evaluate() {
		if (this.stepNumber == 1 && this.objectifList.length != 0) {
			for (let i = 0; i < this.objectifList.length; i++) {
				console.log("saving object ...");
				this.objectifService.evaluate(this.objectifList[i]).subscribe();
				console.log("saved");
			}
			this.authNoticeService.setNotice("Votre modification a été sauvgarder avec success !", 'success');
			setTimeout(() => { this.authNoticeService.setNotice(null, null); }, 4000);
		}
	}

	fixingNewObjectifs() {
		if (this.stepNumber == 2) {
			//Manager can't save an empty new objectif
			for (let i = 0; i < this.newObjectifList.length; i++) {
				if (this.newObjectifList[i].designation == "")
					this.objectifFlag = true;
			}
			this.entretienService.getEntretienByCollaborateur(this.collaborateur).subscribe(entretien => {
				if (entretien.status == "FIXATION_OBJECTIFS") {
					this.newObjectifFixed=true;
					//Manager can pass already saved a new objectif 
					this.objectifFlag=false;
				}
			});
		}
		if (!this.objectifFlag && !this.newObjectifFixed) {
			//Saving new Objectifs
			for (let i = 0; i < this.newObjectifList.length; i++) {
				if (this.savedObjectifs.indexOf(this.newObjectifList[i]) == -1) {
					console.log("saving new object ...");
					console.log(this.newObjectifList[i]);
					this.objectifService.saveNewObjectif(this.newObjectifList[i], this.collaborateur.id).subscribe();
					console.log("saved");
					this.savedObjectifs.push(this.newObjectifList[i]);
				}
			}
			this.authNoticeService.setNotice("Votre modification a été sauvgarder avec success !", 'success');
			setTimeout(() => { this.authNoticeService.setNotice(null, null); }, 4000);
		}
	}

	suivant() {
		//1- EVALUATE 
		this.evaluate();
		this.stepNumber++;
		//2- FIXING NEW OBJECTIFS 
		this.fixingNewObjectifs();
	}

	retour() {
		if (this.stepNumber==1)this.stepNumber;
		else 
		this.stepNumber--;
	}
}

