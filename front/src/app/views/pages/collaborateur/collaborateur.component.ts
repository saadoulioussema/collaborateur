import { UserService } from './../../../core/services/user.service';
import { EntretienService } from './../../../core/services/entretien.service';
import { AuthNotice } from './../../../core/auth/auth-notice/auth-notice.interface';
import { AuthNoticeService } from './../../../core/services/auth-notice.service';
import { ObjectifService } from './../../../core/services/objectif.service';
import { Objectif } from './../../../shared/objectif';
import { Subscription } from 'rxjs';
import { Component, OnInit, Output } from '@angular/core';




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
	tempList: Objectif[] = [];
	objectifs: Objectif[];
	objectif:Objectif;


	constructor(private objectifService: ObjectifService,
				private entretienService: EntretienService,
				private authNoticeService: AuthNoticeService,
				private userService: UserService,) {
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
		this.getCollaborateurObjectifs(parseInt(id));
	}

	getCollaborateurObjectifs(id: number) {
		this.userService.findUserById(id).subscribe(user=>
		this.entretienService.getEntretienByCollaborateur(user).subscribe(entretien=>
		this.objectifService.getObjectifList(entretien.id).subscribe(data=> this.objectifs = data)
		));
	}


	change(objectif: Objectif) {
		let flag = false;
		if (this.tempList.length == 0) {
			this.tempList.push(objectif);
		}
		else {
			for (let i = 0; i < this.tempList.length; i++) {
				if (this.tempList[i] == objectif) {
					console.log("objectif found ! saving changes on a temp list");
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
		for (let i = 0; i < this.tempList.length; i++) {
			console.log("saving ...");
			this.objectifService.saveObjectif(this.tempList[i]).subscribe();
			console.log("saved !");
			this.authNoticeService.setNotice("Votre modification a été sauvgarder avec success !", 'success');
			setTimeout(() => { this.authNoticeService.setNotice(null, null); }, 4000);
		}
	}
}
