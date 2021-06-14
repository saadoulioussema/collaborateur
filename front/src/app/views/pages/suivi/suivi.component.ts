import { Router } from '@angular/router';
import { AuthNoticeService } from './../../../core/services/auth-notice.service';
import { AuthNotice } from './../../../core/services/auth-notice.interface';
import { Subscription } from 'rxjs';
import { Objectif } from './../../../shared/objectif';
import { ObjectifService } from './../../../core/services/objectif.service';

import { Component, OnInit, Output } from '@angular/core';
import { EipsComponent } from '../eips/eips.component';

@Component({
  selector: 'sc-suivi',
  templateUrl: './suivi.component.html',
  styleUrls: ['./suivi.component.scss']
})
export class SuiviComponent implements OnInit {
  // Notice Setup
  @Output() type: any;
  @Output() message: any = '';
  private subscriptions: Subscription[] = [];
  searchText;
  objectifs: Objectif[] = [];
  fakeObjecitfs: Objectif[] = [];
  constructor(private objectifService: ObjectifService, private authNoticeService: AuthNoticeService,  private router: Router,) { }

  ngOnInit(): void {

    // Notice Setup
    this.subscriptions.push(this.authNoticeService.onNoticeChanged$.subscribe(
      (notice: AuthNotice) => {
        notice = Object.assign({}, { message: '', type: '' }, notice);
        this.message = notice.message;
        this.type = notice.type;
      }
    ));
    if (EipsComponent.idEntretien) 
    this.objectifService.getCurrentObjectifList(EipsComponent.idEntretien).subscribe(data => this.objectifs = data);
    else 
    this.router.navigate(["/eips"]);
  }


  change(objectif: Objectif) {
    let flag = false;
    if (this.fakeObjecitfs.length == 0) {
      console.log(" empty list  ! saving changes ");
      objectif.evaluation = objectif.autoEvaluation;
      this.fakeObjecitfs.push(objectif);
    }
    else {
      for (let i = 0; i < this.fakeObjecitfs.length; i++) {
        if (this.fakeObjecitfs[i] == objectif) {
          console.log("objectif found ! saving changes");
          flag = true;
          this.fakeObjecitfs[i].evaluation = objectif.autoEvaluation;
          this.fakeObjecitfs[i].commentaire = objectif.commentaire;
        }
      }
      if (flag == false) {
        console.log("objectif not found time to push a new objectif ");
        objectif.evaluation = objectif.autoEvaluation;
        this.fakeObjecitfs.push(objectif);
      }
    }
  }


  
	saveFeedback() {
		if (this.fakeObjecitfs.length != 0) {
			console.log(this.fakeObjecitfs);
			for (let i = 0; i < this.fakeObjecitfs.length; i++) {
				console.log("saving objectif feedback...");
				this.objectifService.saveFeedback(this.fakeObjecitfs[i]).subscribe();
				console.log("saved");
			}
			this.authNoticeService.setNotice("Votre modification a été sauvgarder avec success !", 'success');
			setTimeout(() => { this.authNoticeService.setNotice(null, null); }, 4000);
		}
	}

}
