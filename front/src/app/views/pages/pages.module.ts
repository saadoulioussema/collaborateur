import { AuthNoticeComponent } from './auth/auth-notice/auth-notice.component';
import { AuthModule } from './auth/auth.module';

import { MatInputModule } from '@angular/material';

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
import { ObjectifService } from '../../services/objectif.service';
import { DirectionService } from './../../services/direction.service';
import { ManagerComponent } from './manager/manager.component';
import { MatSelectModule } from '@angular/material';


@NgModule({
	declarations: [MyPageComponent, CollaborateurComponent, ObjectifComponent, ManagerComponent],
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
		MatSelectModule,
		MatInputModule,
	],
	providers: [ObjectifService,DirectionService]
})
export class PagesModule {
}
