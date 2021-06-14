import { Objectif } from './../../../shared/objectif';
import { User } from './../../../shared/user';
import { ObjectifService } from './../../../core/services/objectif.service';
import { EipsComponent } from './../eips/eips.component';
import { AuthNotice } from './../../../core/auth/auth-notice/auth-notice.interface';
import { AuthNoticeService } from './../../../core/services/auth-notice.service';
import { EntretienService } from './../../../core/services/entretien.service';
import { Subscription } from 'rxjs';
import { Router } from '@angular/router';
import { Component, OnInit, Output, ViewChild, ElementRef, Input } from '@angular/core';

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
	// // Notice Setup
	@Output() type: any;
	@Output() message: any = '';
	// Private properties
	private subscriptions: Subscription[] = [];
	// Manager properties
	entretien: Entretien;
	@ViewChild('wizard', { static: true }) el: ElementRef;

	collaborateur:User;
	objectifs: Objectif[];

	autoEvaluation=false;
	static stepNumber=1;

	feedback :string ;

	constructor(private authNoticeService: AuthNoticeService,private entretienService: EntretienService,private router: Router,private objectifService: ObjectifService,
		private EntretienService: EntretienService
	) {}

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

		this.entretien = EipsComponent.entretien;

		if (this.entretien) {
			// Step 1-1: Prépaer les informations génerales du collaborateur depuis l'entreien pour le manager
			this.entretienService.getCollaborateurByEntretien(EipsComponent.entretien).subscribe(data => {this.collaborateur=data});
			// Step 1-2: Prépaer les objectifs du collaborateur pour le manager 
			this.objectifService.getObjectifList(EipsComponent.entretien.id).subscribe(data => this.objectifs = data);

			if (this.entretien.feedback) this.feedback=this.entretien.feedback ;

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
		});

		wizard.on('afterPrev', (wizardObj) => {
		});
		// Change event
		wizard.on('change', (wizardObj) => {
			setTimeout(() => {
				KTUtil.scrollTop();
			}, 500);
		});
	}

	suivant() {
		ManagerComponent.stepNumber++;
	}

	retour() {
		if (ManagerComponent.stepNumber == 1) ManagerComponent.stepNumber;
		else
		ManagerComponent.stepNumber--;
	}

	onSubmit() {

		this.entretien.feedback=this.feedback;
		this.entretienService.closeEntretien(this.entretien).subscribe();
		this.authNoticeService.setNotice("L'entretien a été cloturé avec success !", 'success');
		setTimeout(() => { this.authNoticeService.setNotice(null, null); }, 12000);
		this.router.navigate(["/eips"]);
	}
}

