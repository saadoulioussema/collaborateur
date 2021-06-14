import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-test-child',
  templateUrl: './test-child.component.html',
  styleUrls: ['./test-child.component.scss']
})
export class TestChildComponent implements OnInit {
  @Input()wizard ;
  constructor() { }

  ngOnInit(): void {

    console.log(this.wizard);
  }


  ngAfterViewInit(): void {
		// Initialize form wizard
		const wizard = new KTWizard(this.wizard, {
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


}
