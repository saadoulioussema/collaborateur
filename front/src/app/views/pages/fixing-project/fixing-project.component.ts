import { EntretienService } from './../../../core/services/entretien.service';
import { AuthNoticeService } from './../../../core/services/auth-notice.service';
import { EipsComponent } from './../eips/eips.component';
import { User } from './../../../shared/user';
import { Entretien } from './../../../shared/entretien';
import { Component, OnInit, ElementRef, ViewChild } from '@angular/core';

const status = ['PROJET_PROFESSIONEL', 'CLOTURE'];


@Component({
	selector: 'app-fixing-project',
	templateUrl: './fixing-project.component.html',
	styleUrls: ['./fixing-project.component.scss']
})
export class FixingProjectComponent implements OnInit {

	collaborateur: User = new User();
	entretien: Entretien;
	projectFlag = false;
	searchText;
	@ViewChild('projet', { static: true }) projet: ElementRef;
	@ViewChild('formation', { static: true }) formation: ElementRef;


	constructor(private authNoticeService: AuthNoticeService, private entretienService: EntretienService,
		projet: ElementRef,
		formation: ElementRef) {
		this.projet = projet;
		this.formation = formation;
	}

	ngOnInit(): void {
		if (EipsComponent.entretien) {
			this.entretien = EipsComponent.entretien;
			this.collaborateur = this.entretien.user;
		}
	}

	fixProjectAndFormation() {
		// let project = " "; let formation = " ";
		let project:string; let formation:string ;
		if (status.includes(this.entretien.status)) this.projectFlag = false;

		if (this.entretien.status = "EVALUATION_COMPETENCES") {
			{
				this.projectFlag = true;
				if (this.projet.nativeElement.value && !this.formation.nativeElement.value) {
					project = this.projet.nativeElement.value;
					this.entretienService.saveProjectAndFormation(project, formation, this.entretien.id).subscribe();
					this.authNoticeService.setNotice("Votre modification a été sauvgarder avec success !", 'success');
					setTimeout(() => { this.authNoticeService.setNotice(null, null); }, 4000);
				}
				else if (this.projet.nativeElement.value && this.formation.nativeElement.value){
					project = this.projet.nativeElement.value;
					formation = this.formation.nativeElement.value;
					this.entretienService.saveProjectAndFormation(project, formation, this.entretien.id).subscribe();
					this.authNoticeService.setNotice("Votre modification a été sauvgarder avec success !", 'success');
					setTimeout(() => { this.authNoticeService.setNotice(null, null); }, 4000);
				}
				else {
					this.authNoticeService.setNotice(" svp définir un projet professionnel pour le collaborateur !", 'danger');
					setTimeout(() => { this.authNoticeService.setNotice(null, null); }, 6000);
				}

			}
		}
	}
}
