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
import { Component, OnInit, Output, ViewChild, ElementRef} from '@angular/core';


import { Objectif } from './../../../shared/objectif';
import { User } from '../../../shared/user';
import { Entretien } from './../../../shared/entretien';

//Service 
import { TranslateService } from '@ngx-translate/core';


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
	fakeDescriptions: Description[] = [];
	descriptions: Description[] = [];
	niveaux: Niveau[];
	evaluationCompetence = false;

	niveauId:number=1;

	constructor(private authNoticeService: AuthNoticeService,
		private entretienService: EntretienService,
		private objectifService: ObjectifService,
		private competenceService: CompetenceService,
		private niveauService: NiveauService,
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
				//Initialiser la liste des newObjectifs au cas le manager retourne vers evaluation
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






	change(objectif: Objectif) {
		this.autoEvaluation = false;
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


	evaluateOldObjectifs() {
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
		if (this.stepNumber == 1) {
			//Manager can't save an empty new objectif
			for (let i = 0; i < this.newObjectifList.length; i++) {
				if (this.newObjectifList[i].designation == "")
					this.objectifFlag = true;
			}
			this.entretienService.getEntretienByCollaborateur(this.collaborateur).subscribe(entretien => {
				if (entretien.status != "ATTENTE_EVALUATION" ||
					entretien.status != "ATTENTE_EVALUATION" ||
					entretien.status != "AUTO_EVALUATION" ||
					entretien.status != "EVALUATION") {
					this.newObjectifFixed = true;
					//Manager can pass already saved a new objectif 
					this.objectifFlag = false;
				}
			});
		}
		// if (!this.objectifFlag && !this.newObjectifFixed) {
		if (this.stepNumber == 2 && !this.objectifFlag) {
			//Saving new Objectifs
			for (let i = 0; i < this.newObjectifList.length; i++) {
				if (this.savedObjectifs.indexOf(this.newObjectifList[i]) == -1 && this.newObjectifList[i].designation != "") {
					console.log("saving new object ...");
					if (EipsComponent.entretien.status == "EVALUATION_COMPETENCES")
						this.objectifService.saveNewObjectif(this.newObjectifList[i], this.collaborateur.id).subscribe();
					console.log("saved");
					this.savedObjectifs.push(this.newObjectifList[i]);
				}
			}

			if (this.savedObjectifs.length > 0 && this.newObjectifList[0].designation != "") {
				this.authNoticeService.setNotice("Votre modification a été sauvgarder avec success !", 'success');
				setTimeout(() => { this.authNoticeService.setNotice(null, null); }, 4000);
			}
		}
	}

	deleteSavedObjectif(savedObj: Objectif) {
		this.savedObjectifs.splice(this.index - 1, 1);
		this.index--;
		this.objectifService.delete(EipsComponent.entretien.id, savedObj.designation).subscribe();
	}



	updateLevels(competence: Competence) {


		if (competence.designation == "Diplôme") {
			this.niveaux = [
				{ "id": 1, "designation": "Inférieur à Bac + 2"},
				{ "id": 2, "designation": "Technicien ou Licence  ( Bac + 3)"},
				{ "id": 3, "designation": "Maitrise ( Bac + 4: Ancien Régime)"},
				{ "id": 4, "designation": "Ingénieur Ou master"},
				{ "id": 5, "designation": "Ingénieur Grandes Ecoles à l'internationales/Doctorat/E-MBA"}
			];
		}

		if (competence.designation == "Expérience Professionnelle") {
			this.niveaux = [
				{ "id": 1, "designation": "Inférieur à 2 ans ou Inférieur à 1 an à Sofrecom"},
				{ "id": 2, "designation": "Entre 2 à 5 ans ou de 1 à 4 ans avec 1 an à Sofrecom"},
				{ "id": 3, "designation": "Entre 5 à 8 ans ou de 3 à 6 ans avec 2 ans à Sofrecom"},
				{ "id": 4, "designation": "Entre 8 à 12 ans ou de 6 à 10 ans avec 2 ans à Sofrecom"},
				{ "id": 5, "designation": "Plus que 12 ans ou plus que 10 ans avec 2 ans à Sofrecom"}
			];
		}
	}




	saveCompetenceEvaluations(competence: Competence, niveauId: number) {
		console.log("Competence :", competence, ",selected level : ", niveauId);
		this.evaluationCompetence = true;
		
		let flag = false;
		let description: Description = new Description();
		let descriptionPk: DescriptionPK = new DescriptionPK();
		let designation= this.niveaux.find(i=>i.id === niveauId).designation;

		description.descriptionPK = descriptionPk;
		description.descriptionPK.idCompetence = competence.id;
		description.descriptionPK.idNiveau = niveauId;
		description.description=designation;
		
		

		if (this.fakeDescriptions.length == 0) {
			console.log(" empty list  ! saving changes ");
			this.fakeDescriptions.push(description);
		}
		else {
			for (let i = 0; i < this.fakeDescriptions.length; i++) {
				if (this.fakeDescriptions[i].descriptionPK.idCompetence == description.descriptionPK.idCompetence) {
					console.log("competence found ! saving new level");
					flag = true
					console.log("updatig description ...");
					this.fakeDescriptions[i].description=designation;
					this.descriptionService.updateDescriptionLevel(this.fakeDescriptions[i], description.descriptionPK.idNiveau).subscribe();
					this.fakeDescriptions[i].descriptionPK.idNiveau = description.descriptionPK.idNiveau;
					console.log("saved");
				}
				if (flag == false) {
					console.log("description not found time to push a new description");
					this.fakeDescriptions.push(description);
				}
			}
		}
	}

	evaluateCompetence() {
		if (this.stepNumber == 3 && this.fakeDescriptions.length != 0) {
			for (let i = 0; i < this.fakeDescriptions.length; i++) {
				this.descriptionService.getDescription(this.fakeDescriptions[i]).subscribe(data => {
					if (!data) {
						console.log("saving new description ...");
						this.descriptionService.newDescription(this.fakeDescriptions[i], EipsComponent.entretien.id).subscribe();
						console.log("saved");
						this.entretienService.getCollaborateurByEntretien(EipsComponent.entretien).subscribe(collaborateur => {
							console.log("saving new evaluation ...");
							this.collaborateur = collaborateur;
							this.evaluationService.newEvaluation(collaborateur.id, this.fakeDescriptions[i].descriptionPK.idCompetence).subscribe();
							console.log("saved");
						});
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

