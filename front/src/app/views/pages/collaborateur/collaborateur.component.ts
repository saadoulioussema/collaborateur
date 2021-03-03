import { AfterViewInit, Component, ElementRef, OnInit, ViewChild } from '@angular/core';

@Component({
  selector: 'kt-collaborateur',
  templateUrl: './collaborateur.component.html',
  styleUrls: ['./collaborateur.component.scss']
})
export class CollaborateurComponent implements OnInit {
	@ViewChild('wizard', {static: true}) el: ElementRef;
  constructor() { }

	model: any = {
		projetProfessionel: '',
		formation: '',
		certification: '',
		objectif: '',
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


	ngOnInit() {
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


