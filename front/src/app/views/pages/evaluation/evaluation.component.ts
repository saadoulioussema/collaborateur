import { AuthNotice } from './../../../core/services/auth-notice.interface';
import { Subscription } from 'rxjs';
import { ObjectifService } from './../../../core/services/objectif.service';
import { AuthNoticeService } from './../../../core/services/auth-notice.service';
import { Objectif } from './../../../shared/objectif';
import { User } from './../../../shared/user';
import { Component, Input,Output } from '@angular/core';

@Component({
	selector: 'app-evaluation',
	templateUrl: './evaluation.component.html',
	styleUrls: ['./evaluation.component.scss']
})
export class EvaluationComponent {

	static collaborateur: User = new User();
	
	
	@Input()  collaborateur:User;
	@Input() objectifs: Objectif[];


	// Notice Setup
	@Output() type: any;
	@Output() message: any = '';
	private subscriptions: Subscription[] = [];


	//Les objectifs qui seront validés par le manager (preparing a temp list using change to send it after)
	fakeObjectifs: Objectif[] = [];
	
	autoEvaluation = false;




	constructor(private objectifService: ObjectifService,
		private authNoticeService: AuthNoticeService) { }

	ngOnInit(): void {}

	changeObjectif(objectif: Objectif) {
		this.autoEvaluation = true;
		let flag = false;
		if (this.fakeObjectifs.length == 0) {
			console.log(" empty list  ! saving changes ");
			objectif.evaluation = objectif.autoEvaluation;
			this.fakeObjectifs.push(objectif);
		}
		else {
			for (let i = 0; i < this.fakeObjectifs.length; i++) {
				if (this.fakeObjectifs[i] == objectif) {
					console.log("objectif found ! saving changes");
					flag = true;
					this.fakeObjectifs[i].evaluation = objectif.autoEvaluation;
					this.fakeObjectifs[i].commentaire = objectif.commentaire;
				}
			}
			if (flag == false) {
				console.log("objectif not found time to push a new objectif ");
				objectif.evaluation = objectif.autoEvaluation;
				this.fakeObjectifs.push(objectif);
			}
		}
	}

	evaluateOldObjectifs() {
		if (this.fakeObjectifs.length != 0) {
			for (let i = 0; i < this.fakeObjectifs.length; i++) {
				console.log("saving objectif ...");
				this.objectifService.evaluate(this.fakeObjectifs[i]).subscribe();
				console.log("saved");
			}
			this.authNoticeService.setNotice("Votre modification a été sauvgarder avec success !", 'success');
			setTimeout(() => { this.authNoticeService.setNotice(null, null); }, 4000);
		}
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
		this.evaluateOldObjectifs();
	}
}
