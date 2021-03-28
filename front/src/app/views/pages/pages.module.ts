

// Angular
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule} from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { MatSelectModule } from '@angular/material';
import { MatInputModule, MatButtonModule } from '@angular/material';
// Partials
import { PartialsModule } from '../partials/partials.module';
// Pages
import { CoreModule } from '../../core/core.module';
import { MailModule } from './apps/mail/mail.module';
import { ECommerceModule } from './apps/e-commerce/e-commerce.module';
import { UserManagementModule } from './user-management/user-management.module';
import { ManagerComponent } from './manager/manager.component';
import { MyPageComponent } from './my-page/my-page.component';
import { CollaborateurComponent } from './collaborateur/collaborateur.component';
import { ObjectifComponent } from './objectif/objectif.component';
//Services
import { ObjectifService } from '../../services/objectif.service';
import { DirectionService } from './../../services/direction.service';




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
		MatButtonModule,
	],
	providers: [ObjectifService,DirectionService]
})
export class PagesModule {
}
