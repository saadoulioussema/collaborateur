import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';

@Component({
  selector: 'app-test',
  templateUrl: './test.component.html',
  styleUrls: ['./test.component.scss']
})
export class TestComponent implements OnInit {

  @ViewChild('wizard', { static: true }) el: ElementRef;

  wizard ;

  constructor() { }

  ngOnInit(): void {

	this.wizard = this.el ;
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

}
