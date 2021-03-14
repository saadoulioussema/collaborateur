import { AuthNotice } from './../../../services/auth-notice.interface';
import { Subscription } from 'rxjs';
import { TranslateService } from '@ngx-translate/core';
import { Router } from '@angular/router';
import { Component, OnInit, Output, ChangeDetectorRef } from '@angular/core';
import { Objectif } from './../../../shared/objectif';
import { User } from './../../../shared/user';
import { Entretien } from './../../../shared/entretien';
import { EntretienService } from './../../../services/entretien.service';
import { ObjectifService } from './../../../services/objectif.service';
import { AuthNoticeService } from './../../../services/auth-notice.service';


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

	entretien: Entretien;
	collaborateur: User = null;
	objectifs: Objectif[];
	managerId: string;
	EIPs: Entretien[];
	tempList: Array<Objectif> = [];


	constructor(private entretienService: EntretienService, private objectifService: ObjectifService,private router: Router,private authNoticeService: AuthNoticeService,
		private translate: TranslateService) { }

	ngOnInit() {
		// Notice Setup
		this.subscriptions.push(this.authNoticeService.onNoticeChanged$.subscribe(
			(notice: AuthNotice) => {
				notice = Object.assign({}, {message: '', type: ''}, notice);
				this.message = notice.message;
				this.type = notice.type;
			}
		));		
		this.authNoticeService.setNotice("vous pouvez cliquer sur un collaborateur pour plus de details !", 'info');


		this.managerId = localStorage.getItem("managerId");
		let id = parseInt(this.managerId)
		this.getEIPs(id);
	}



	getCollaborateur(entretien: Entretien) {
		this.entretienService.getCollaborateurByEntretien(entretien).subscribe(data => {
			this.collaborateur = data,
			this.findHisObjectif(this.collaborateur.id),
			this.entretien=entretien;
		});
	}

	findHisObjectif(id: number) {
		this.objectifService.getCollaborateurObjectifsForManager(id).subscribe(data => {
			this.objectifs = data
		});
	}




	getEIPs(id: number) {
		this.entretienService.getEntretienList(id).subscribe(data => {
			this.EIPs = data,
				console.log("Entretien List  : ", this.EIPs);
		});
	}

	change(objectif: Objectif) {
		let flag = false;
		if (this.tempList.length == 0) {
			console.log(" empty list  ! saving changes on a temp list  list ");
			this.tempList.push(objectif);
		}
		else {
			for (let i = 0; i < this.tempList.length; i++) {
				if (this.tempList[i] == objectif) {
					console.log("objectif found ! saving changes on a temp list  list ");
					flag = true;
					this.tempList[i].evaluation = objectif.autoEvaluation;
					this.tempList[i].commentaire = objectif.commentaire;
				}
				if (flag == false) {
					console.log("objectif not found time to push a new objectif ");
					this.tempList.push(objectif);
				}
			}
		}
	}

	evaluate(entretien:Entretien) {
		console.log("in evaluate method ");
		for (let i = 0; i < this.tempList.length; i++) {
			console.log("saving object ...");
			this.objectifService.saveObjectif(this.tempList[i]).subscribe(this.collaborateur = null);
			console.log("saved");
		}
		console.log("saving EIP details ...");
		this.entretienService.saveEntretien(entretien).subscribe(this.entretien=null);
		console.log("saved");
		this.authNoticeService.setNotice("Votre modification a été sauvgarder avec success !", 'success');
		setTimeout(()=>{this.authNoticeService.setNotice("vous pouvez cliquer sur un collaborateur pour plus de details !", 'info');}, 4000);
	}
}
