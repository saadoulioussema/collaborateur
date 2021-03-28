import { Subscription } from 'rxjs';
import { AuthNoticeService } from './../../../services/auth-notice.service';
import { Objectif } from './../../../shared/objectif';
import { ObjectifService } from './../../../services/objectif.service';
import { Component, OnInit, Output } from '@angular/core';
import { AuthNotice } from './../../../services/auth-notice.interface';



@Component({
	selector: 'sc-collaborateur',
	templateUrl: './collaborateur.component.html',
	styleUrls: ['./collaborateur.component.scss']
})
export class CollaborateurComponent implements OnInit {
	// Notice Setup
	@Output() type: any;
	@Output() message: any = '';
	private subscriptions: Subscription[] = [];
	tempList: Array<Objectif> = [];
	objectifs: Objectif[];
	objectif:Objectif;
	submitted = false;

	constructor(private objectifService: ObjectifService,private authNoticeService: AuthNoticeService) {
	}

	ngOnInit() {

		// Notice Setup
		this.subscriptions.push(this.authNoticeService.onNoticeChanged$.subscribe(
			(notice: AuthNotice) => {
				notice = Object.assign({}, { message: '', type: '' }, notice);
				this.message = notice.message;
				this.type = notice.type;
			}
		));
		this.authNoticeService.setNotice("autoEvaluez vos objectifs !", 'info');


		let id = localStorage.getItem("Id");
		this.getAllObjectifs(parseInt(id));
	}

	getAllObjectifs(id: number) {
		this.objectifService.getObjectifsList(id).subscribe(data => {
			this.objectifs = data,
				console.log("Objectif List  : ", this.objectifs);
		});
	}


	change(objectif: Objectif) {
		console.log("Commentaire de l objectif changé ====>",objectif.commentaire)
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

	autoEvaluate() {
		console.log("in auto evaluate method ");
		for (let i = 0; i < this.tempList.length; i++) {
			console.log("saving ...");
			this.objectifService.saveObjectif(this.tempList[i]).subscribe();
			console.log("saved !");
			this.authNoticeService.setNotice("Votre modification a été sauvgarder avec success !", 'success');
			setTimeout(() => { this.authNoticeService.setNotice(null, null); }, 4000);
		}
	}


	onSubmit() {
		this.submitted = true;
	}



}
