// Angular
import { NgModule, } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
// Metronic
import { PartialsModule } from '../../partials/partials.module';
import { CoreModule } from '../../../core/core.module';
import { WizardComponent } from './wizard.component';
import { Wizard3Component } from './wizard3/wizard3.component';
import { MatSelectModule, MatInputModule } from '@angular/material';

@NgModule({
	declarations: [
		WizardComponent,
		Wizard3Component,
	],
	imports: [
		CommonModule,
		FormsModule,
		PartialsModule,
		CoreModule,
		RouterModule.forChild([
			{
				path: '',
				component: WizardComponent,
				children: [
					{
						path: 'wizard-3',
						component: Wizard3Component
					},

				]
			},
		]),
		MatSelectModule,
		MatInputModule
	]
})
export class WizardModule {
}
