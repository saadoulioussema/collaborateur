import { EipsComponent } from './../eips/eips.component';
import { EvaluationService } from './../../../core/services/evaluation.service';
import { AuthNoticeService } from './../../../core/services/auth-notice.service';
import { User } from './../../../shared/user';
import { EvaluationPK } from './../../../shared/evaluationPK';
import { DescriptionService } from './../../../core/services/description.service';
import { CompetenceService } from './../../../core/services/competence.service';
import { Niveau } from './../../../shared/niveau';
import { Description } from './../../../shared/description';
import { Evaluation } from './../../../shared/evaluation';
import { Competence } from './../../../shared/competence';
import { Component, OnInit } from '@angular/core';

const status = ['EVALUATION_COMPETENCES', 'PROJET_PROFESSIONEL', 'CLOTURE'];


@Component({
	selector: 'app-evaluation-competences',
	templateUrl: './evaluation-competences.component.html',
	styleUrls: ['./evaluation-competences.component.scss']
})
export class EvaluationCompetencesComponent implements OnInit {

	collaborateur: User;
	//Evaluation des compétences
	competences: Competence[];
	fakeEvaluations: Evaluation[] = [];
	descriptions: Description[] = [];
	niveaux: Niveau[];
	evaluationCompetence = false;
	niveauId: number = 1;
	searchText;

	evaluations: Evaluation[];
	evaluatedCompetences: Competence[] = [];
	evaluatedCompetencesDesc: Description[] = [];

	competence: Competence;
	evaluatedObjectifs = false;

	constructor(private competenceService: CompetenceService,
		private descriptionService: DescriptionService,
		private authNoticeService: AuthNoticeService,
		private evaluationService: EvaluationService) { }

	ngOnInit(): void {
		if (EipsComponent.entretien) {
			if (status.includes(EipsComponent.entretien.status)) {
				this.getCollaborateurEvaluations();
				this.evaluatedObjectifs = true;
			}
			this.collaborateur = EipsComponent.entretien.user;
			this.competenceService.getCompetenceList().subscribe(data => this.competences = data);
		}
	}



	getCollaborateurEvaluations() {
		//get collaborateur evaluations ( compétences et niveaux ) ==> evaluated compétences
		this.evaluationService.getEvaluationListByCollaborateur(EipsComponent.entretien.user.id).subscribe(data => {
			if (data) {
				this.evaluations = data;
				for (let i = 0; i < this.evaluations.length; i++) {
					this.competenceService.getCompetence(this.evaluations[i].evaluationPK.idCompetence).subscribe(data => {
						this.competence = data;
						this.competence.niveau = this.evaluations[i].niveau;
						this.evaluatedCompetences.push(this.competence);
					});
				}
			}
		});
	}




	updateLevels(competence: Competence) {
		this.descriptionService.getDescriptionByCompetence(competence).subscribe(data => this.descriptions = data);
	}


	updateEvaluation(evaluation:Evaluation){
		this.evaluationService.getEvaluation(evaluation).subscribe(data => {
			if (data) {
				console.log("competence found ! saving new level");
				console.log("updatig evaluation ...");
				this.evaluationService.updateEvaluationLevel(evaluation).subscribe();
				console.log("saved");
				this.authNoticeService.setNotice("modification sauvgardé !", 'warning');
				setTimeout(() => { this.authNoticeService.setNotice(null, null); }, 4000);
			}
		});
	}

	saveCompetenceEvaluations(competence: Competence, niveauId: number) {
		this.evaluationCompetence = true;
		let flag = false;

		let evaluation: Evaluation = new Evaluation();
		let evaluationPK: EvaluationPK = new EvaluationPK();

		evaluation.evaluationPK = evaluationPK;
		evaluation.evaluationPK.idUser = this.collaborateur.id;
		evaluation.evaluationPK.idCompetence = competence.id;
		evaluation.niveau = niveauId;



		if (this.fakeEvaluations.length == 0) {
			this.updateEvaluation(evaluation);
			console.log(" empty list  ! saving changes ");
			this.fakeEvaluations.push(evaluation);
			this.authNoticeService.setNotice("modification sauvgardé !", 'warning');
			setTimeout(() => { this.authNoticeService.setNotice(null, null); }, 4000);
		}
		else {
			for (let i = 0; i < this.fakeEvaluations.length; i++) {
				this.evaluationService.getEvaluation(this.fakeEvaluations[i]).subscribe(data => {
					if (data) {
						flag = true
						this.fakeEvaluations[i].niveau = niveauId;
						this.updateEvaluation(this.fakeEvaluations[i]);
					}
				});
			}
			if (flag == false) {
				this.updateEvaluation(evaluation);
				console.log("evaluation not found time to push a new evaluation");
				this.fakeEvaluations.push(evaluation);
				this.authNoticeService.setNotice("modification sauvgardé !", 'warning');
				setTimeout(() => { this.authNoticeService.setNotice(null, null); }, 4000);
			}
		}
	}

	evaluateCompetence() {
		//zeyda l condition 2 puisque lorique un collab a évaluer ses competences il aura un autre girlle d"évaluation et aura la possibilite de mosifier directement
		if (this.fakeEvaluations.length != 0 && !status.includes(EipsComponent.entretien.status)) {
			for (let i = 0; i < this.fakeEvaluations.length; i++) {
				this.evaluationService.getEvaluation(this.fakeEvaluations[i]).subscribe(data => {
					console.log("mon data =>",data);
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


}
