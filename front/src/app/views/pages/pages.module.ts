
// Angular
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
// Partials
import { PartialsModule } from '../partials/partials.module';
// Pages
import { CoreModule } from '../../core/core.module';
import { MailModule } from './apps/mail/mail.module';
import { ECommerceModule } from './apps/e-commerce/e-commerce.module';
import { UserManagementModule } from './user-management/user-management.module';
import { MyPageComponent } from './my-page/my-page.component';
import { CollaborateurComponent } from './collaborateur/collaborateur.component';
import { ObjectifComponent } from './objectif/objectif.component';
import { TestComponent } from './test/test.component';
import { ObjectifService } from '../../services/objectif.service';
import { DirectionService } from './../../services/direction.service';

@NgModule({
	declarations: [MyPageComponent, CollaborateurComponent, ObjectifComponent, TestComponent],
	exports: [],
	imports: [
		CommonModule,
		HttpClientModule,
		FormsModule,
		CoreModule,
		PartialsModule,
		MailModule,
		ECommerceModule,
		UserManagementModule,
	],
	providers: [ObjectifService,DirectionService]
})
export class PagesModule {
}
