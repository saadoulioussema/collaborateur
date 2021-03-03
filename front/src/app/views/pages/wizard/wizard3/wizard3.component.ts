import { ObjectifService } from './../../../../services/objectif.service';
import { ObjectifComponent } from './../../objectif/objectif.component';
import { Objectif } from './../../../../shared/objectif';
import { AfterViewInit, Component, ElementRef, OnInit, ViewChild } from '@angular/core';



@Component({
	selector: 'kt-wizard3',
	templateUrl: './wizard3.component.html',
	styleUrls: ['./wizard3.component.scss']
})
export class Wizard3Component implements OnInit, AfterViewInit {
	@ViewChild('wizard', {static: true}) el: ElementRef;

	objectifs:Objectif[];
	objectif:Objectif;
	model: any = {
		projetProfessionel: '',
		formation: '',
		certification: '',
		// designation: '',
		evaluation: 'NONE',
		commentaire:'',
		collaborateur: '',
		package: 'Complete Workstation (Monitor, Computer, Keyboard & Mouse)',
		weight: '25',
		width: '110',
		height: '90',
		length: '150',
		delivery: 'overnight',
		packaging: 'regular',
		preferreddelivery: 'morning',
		locaddress1: 'Address Line 1',
		locaddress2: 'Address Line 2',
		locpostcode: '3072',
		loccity: 'Preston',
		locstate: 'VIC',
		loccountry: 'AU',
	};
	submitted = false;

	constructor(private objectifService : ObjectifService) {
	}

	

	ngOnInit() {
		this.getAllObjectifs();

	}

	getAllObjectifs(){
		this.objectifService.getObjectifsList().subscribe(data=>{
		this.objectifs=data,
		console.log( "lista taa les objectifs : ",this.objectifs);
	  });
	  }


	  autoEvaluate(objectif:Objectif){
		this.objectif=objectif;
		this.objectifService.saveObjectif(objectif).subscribe();
	  }

	ngAfterViewInit(): void {
		// Initialize form wizard
		const wizard = new KTWizard(this.el.nativeElement, {
			startStep: 1
		});

		// Validation before going to next page
		wizard.on('beforeNext', (wizardObj) => {
			// https://angular.io/guide/forms
			// https://angular.io/guide/form-validation

			// validate the form and use below function to stop the wizard's step
			// wizardObj.stop();
		});

		// Change event
		wizard.on('change', () => {
			setTimeout(() => {
				KTUtil.scrollTop();
			}, 500);
		});
	}

	onSubmit() {
		this.submitted = true;
	}
}
