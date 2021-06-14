import { User } from './../../../shared/user';
import { UserService } from './../../../core/services/user.service';
import { ObjectifService } from './../../../core/services/objectif.service';
import { Objectif } from './../../../shared/objectif';
import { EntretienService } from './../../../core/services/entretien.service';
import { AuthNoticeService } from './../../../core/services/auth-notice.service';
import { AuthNotice } from './../../../core/auth/auth-notice/auth-notice.interface';
import { Entretien } from './../../../shared/entretien';

import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Component, OnInit, Output, ElementRef, ViewChild } from '@angular/core';


@Component({
  selector: 'sc-eips',
  templateUrl: './eips.component.html',
  styleUrls: ['./eips.component.scss']
})
export class EipsComponent implements OnInit {

  // Notice Setup
  @Output() type: any;
  @Output() message: any = '';
  // Private properties
  private subscriptions: Subscription[] = [];
  @ViewChild('wizard', { static: true }) el: ElementRef;
  EIPs: Entretien[];
  managerId: string;
  objectifs: Objectif[];
  tempList: Objectif[] = [];
  static entretien: Entretien = null;
  searchText;

  collabs: User[] = [];
  static idEntretien: number;
  constructor(private authNoticeService: AuthNoticeService,
    private entretienService: EntretienService,
    private router: Router,
    private objectifService: ObjectifService,
    private userService: UserService,) { }

  ngOnInit() {
    // Notice Setup
    this.subscriptions.push(this.authNoticeService.onNoticeChanged$.subscribe(
      (notice: AuthNotice) => {
        notice = Object.assign({}, { message: '', type: '' }, notice);
        this.message = notice.message;
        this.type = notice.type;
      }
    ));

    // Step 1 : Evaluation : prépaer la liste des EIPs pour le manager 
    this.managerId = localStorage.getItem("managerId");
    let id = parseInt(this.managerId);
    this.getEIPs(id);

    // Step 2 : Auto Evaluation : prépaer les objectifs du manager 
    this.getManagerObjectifs(id);


    // Step 3": Manager equipe de la compagne en cours
    this.userService.getEquipeEnCours(id).subscribe(data => this.collabs = data );
  }

  ngAfterViewInit(): void {
    // Initialize form wizard
    const wizard = new KTWizard(this.el.nativeElement, {
      startStep: 1
    });

    wizard.on('beforeNext', (wizardObj) => {
    });

    // Change event
    wizard.on('change', (wizardObj) => {
      setTimeout(() => {
        KTUtil.scrollTop();
      }, 500);
    });
  }




  getEIPs(id: number) {
    this.entretienService.getEntretienList(id).subscribe(data => {this.EIPs = data,console.log(this.EIPs)});
  }


  copyEntretien(entretien: Entretien) {
    EipsComponent.entretien = entretien;
    this.router.navigate(["/evaluate"]);
  }

  getManagerObjectifs(id: number) {
    this.userService.findUserById(id).subscribe(user =>
      this.entretienService.getEntretienByCollaborateur(user).subscribe(entretien => {
        if (entretien)
          this.objectifService.getObjectifList(entretien.id).subscribe(data => this.objectifs = data);
      }));
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


  toSuivi(id: number) {
    EipsComponent.idEntretien = id;
    this.router.navigate(["/suivi"]);
  }
}
