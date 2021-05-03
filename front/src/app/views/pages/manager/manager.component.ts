import { EvaluationPK } from './../../../shared/evaluationPK';
import { Evaluation } from './../../../shared/evaluation';
import { EvaluationService } from './../../../core/services/evaluation.service';
import { DescriptionPK } from './../../../shared/descriptionPK';
import { DescriptionService } from './../../../core/services/description.service';
import { Description } from './../../../shared/description';
import { NiveauService } from './../../../core/services/niveau.service';
import { Niveau } from './../../../shared/niveau';
import { CompetenceService } from './../../../core/services/competence.service';
import { Competence } from './../../../shared/competence';
import { EipsComponent } from './../eips/eips.component';
import { AuthNotice } from './../../../core/auth/auth-notice/auth-notice.interface';
import { AuthNoticeService } from './../../../core/services/auth-notice.service';
import { ObjectifService } from './../../../core/services/objectif.service';
import { EntretienService } from './../../../core/services/entretien.service';
import { Subscription } from 'rxjs';
import { Router } from '@angular/router';
import { Component, OnInit, Output, ViewChild, ElementRef } from '@angular/core';


import { Objectif } from './../../../shared/objectif';
import { User } from '../../../shared/user';
import { Entretien } from './../../../shared/entretien';

//Service 
import { TranslateService } from '@ngx-translate/core';
import { THIS_EXPR } from '@angular/compiler/src/output/output_ast';

const status = ['ATTENTE_EVALUATION', 'AUTO_EVALUATION', 'EVALUATION'];



@Component({
	selector: 'sc-manager',
	templateUrl: './manager.component.html',
	styleUrls: ['./manager.component.scss'],
	//to activate tooltip or choose ng deep in scss 
	// encapsulation: ViewEncapsulation.None
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
	savedObjectifs: Objectif[] = [];
	autoEvaluation = false;
	newObjectifFixed = false;

	//Evaluation des compétences
	competences: Competence[];
	fakeEvaluations: Evaluation[] = [];
	descriptions: Description[] = [];
	niveaux: Niveau[];
	evaluationCompetence = false;
	niveauId: number = 1;

	constructor(private authNoticeService: AuthNoticeService,
		private entretienService: EntretienService,
		private objectifService: ObjectifService,
		private competenceService: CompetenceService,
		private evaluationService: EvaluationService,
		private descriptionService: DescriptionService,
		private router: Router,
		private translate: TranslateService,
	) { }



	ngOnInit() {
		// Notice Setup
		this.subscriptions.push(this.authNoticeService.onNoticeChanged$.subscribe(
			(notice: AuthNotice) => {
				notice = Object.assign({}, { message: '', type: '' }, notice);
				this.message = notice.message;
				this.type = notice.type;
			}
		));
		this.authNoticeService.setNotice("Evaluez le collaborateur !", 'info');
		setTimeout(() => { this.authNoticeService.setNotice(null, null); }, 4000);
		if (EipsComponent.entretien) {
			// Step 0 : Le manager doit evaluer le collaborateur si il a autoévaluer ses objectifs avant de passer 
			if (EipsComponent.entretien.status == "AUTO_EVALUATION" || EipsComponent.entretien.status == "ATTENTE_EVALUATION")
				this.autoEvaluation = true;
			// Step 1-1: Prépaer les informations génerales du collaborateur depuis l'entreien pour le manager
			this.entretienService.getCollaborateurByEntretien(EipsComponent.entretien).subscribe(collaborateur => this.collaborateur = collaborateur);
			// Step 1-2: Prépaer les objectifs du collaborateur pour le manager 
			this.objectifService.getObjectifList(EipsComponent.entretien.id).subscribe(data => this.objectifs = data);
			// Step 2 : Prépaer un champ de saisie initial pour le manager 
			this.obj = new Objectif();
			this.newObjectifList.push(this.obj);
			this.index++;
			// Step 3 : Préparer liste des niveaux et compétences
			// this.niveauService.getNiveauList().subscribe(data => this.niveaux = data);
			this.competenceService.getCompetenceList().subscribe(data => this.competences = data);
		}
		else
			this.router.navigate(["/eips"]);
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
			if (this.stepNumber == 1) {
				//Initialiser la liste des objectifs au cas le manager retourne vers evaluate
				//Forcer la longeur de la liste à zéro 
				this.objectifList = [];
				//Initialiser la liste des newObjectifs au cas le manager retourne vers evaluation
				this.newObjectifList = [];
				this.obj = new Objectif();
				this.newObjectifList.push(this.obj);
				this.index++;
			}
			if (this.stepNumber == 2) {
				//Initialiser la liste des newObjectifs au cas le manager retourne vers fixation des objectifs
				this.newObjectifList = [];
				this.index = 0;
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

	changeObjectif(objectif: Objectif) {
		this.autoEvaluation = false;
		let flag = false;
		if (this.objectifList.length == 0) {
			console.log(" empty list  ! saving changes ");
			objectif.evaluation = objectif.autoEvaluation;
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
			}
			if (flag == false) {
				console.log("objectif not found time to push a new objectif ");
				objectif.evaluation = objectif.autoEvaluation;
				this.objectifList.push(objectif);
			}
		}
	}

	evaluateOldObjectifs() {
		if (this.stepNumber == 1 && this.objectifList.length != 0) {
			console.log(this.objectifList);
			for (let i = 0; i < this.objectifList.length; i++) {
				console.log("saving objectif ...");
				this.objectifService.evaluate(this.objectifList[i]).subscribe();
				console.log("saved");
			}
			this.authNoticeService.setNotice("Votre modification a été sauvgarder avec success !", 'success');
			setTimeout(() => { this.authNoticeService.setNotice(null, null); }, 4000);
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



	fixingNewObjectifs() {
		if (!status.includes(EipsComponent.entretien.status)) {
			//Manager can go to next step or add others new objectifs
			this.newObjectifFixed = true;
		}
		//Manager can't save an empty new objectif
		for (let i = 0; i < this.newObjectifList.length; i++) {
			if (this.newObjectifList[i].designation == "")
				this.objectifFlag = true;
		}
		if (this.stepNumber == 2 && !this.objectifFlag) {
			if (this.newObjectifFixed) {
				//Saving ohter new Objectifs
				console.log("saving other new objectifs...");
				this.objectifService.saveOtherNewObjectifs(this.newObjectifList,this.collaborateur.id).subscribe();
				// this.objectifService.saveNewObjectif(this.newObjectifList,0,EipsComponent.entretien.id).subscribe();
				console.log("saved");
				for (let i = 0; i < this.newObjectifList.length; i++) {
					this.savedObjectifs.push(this.newObjectifList[i]);
				}
				this.authNoticeService.setNotice("Votre modification a été sauvgarder avec success !", 'success');
				setTimeout(() => { this.authNoticeService.setNotice(null, null); }, 4000);
			}
			else {
				//Saving new Objectifs
				let entretien: Entretien = new Entretien();
				this.entretienService.newEntretien(entretien).subscribe(entretien => {
					console.log("saving new objectifs...");
					this.objectifService.saveNewObjectifs(this.newObjectifList, entretien.id, EipsComponent.entretien.id).subscribe();
					EipsComponent.entretien.status = "FIXATION_OBJECTIFS";
					console.log("saved");
					this.savedObjectifs = this.newObjectifList;
					this.authNoticeService.setNotice("Votre modification a été sauvgarder avec success !", 'success');
					setTimeout(() => { this.authNoticeService.setNotice(null, null); }, 4000);
				});
			}
		}
	}

	deleteSavedObjectif(savedObj: Objectif) {
		this.savedObjectifs.splice(this.index - 1, 1);
		this.index--;
		this.objectifService.delete(this.collaborateur.id, savedObj.designation).subscribe();
	}

	updateLevels(competence: Competence) {
		this.descriptionService.getDescriptionByCompetence(competence).subscribe(data => this.descriptions = data);
	}

	saveCompetenceEvaluations(competence: Competence, niveauId: number) {
		console.log("Competence :", competence, ",selected level : ", niveauId);
		this.evaluationCompetence = true;
		let flag = false;

		let evaluation: Evaluation = new Evaluation();
		let evaluationPK: EvaluationPK = new EvaluationPK();

		evaluation.evaluationPK = evaluationPK;
		evaluation.evaluationPK.idUser = this.collaborateur.id;
		evaluation.evaluationPK.idCompetence = competence.id;
		evaluation.niveau = niveauId;

		if (this.fakeEvaluations.length == 0) {
			console.log(" empty list  ! saving changes ");
			this.fakeEvaluations.push(evaluation);
		}
		else {
			for (let i = 0; i < this.fakeEvaluations.length; i++) {
				if (this.fakeEvaluations[i].evaluationPK.idCompetence == evaluation.evaluationPK.idCompetence) {
					console.log("competence found ! saving new level");
					flag = true
					console.log("updatig evaluation ...");
					this.fakeEvaluations[i].niveau = niveauId;
					this.evaluationService.updateEvaluationLevel(this.fakeEvaluations[i]).subscribe();
					console.log("saved");

					console.log("list after updating levels ...", this.fakeEvaluations);
				}
			}
			if (flag == false) {
				console.log("evaluation not found time to push a new evaluation");
				this.fakeEvaluations.push(evaluation);
			}
		}
	}

	evaluateCompetence() {
		if (this.stepNumber == 3 && this.fakeEvaluations.length != 0) {
			console.log(this.fakeEvaluations);
			for (let i = 0; i < this.fakeEvaluations.length; i++) {
				this.evaluationService.getEvaluation(this.fakeEvaluations[i]).subscribe(data => {
					if (!data) {
						console.log("saving new evaluation ...");
						this.fakeEvaluations[i].evaluationPK.idUser = this.collaborateur.id;
						this.evaluationService.newEvaluation(this.fakeEvaluations[i]).subscribe();
						console.log("saved");
					}
				});
			}
			this.authNoticeService.setNotice("Votre modification a été sauvgarder avec success !", 'success');
			setTimeout(() => { this.authNoticeService.setNotice(null, null); }, 4000);
		}
	}

	suivant() {
		//1- EVALUATE OLD OBJECTIFS
		this.evaluateOldObjectifs();
		//2- FIXING NEW OBJECTIFS 
		this.fixingNewObjectifs();
		//3- EVALUATE COMPETENCES
		this.evaluateCompetence();
		this.stepNumber++;

	}

	retour() {
		if (this.stepNumber == 1) this.stepNumber;
		else
			this.stepNumber--;
	}

	onSubmit() {
	}
}

