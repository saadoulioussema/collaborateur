import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
// Angular
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule} from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
// Partials
import { PartialsModule } from '../partials/partials.module';
// Pages
import { CoreModule } from '../../core/core.module';
import { ManagerComponent } from './manager/manager.component';
import { MyPageComponent } from './my-page/my-page.component';
import { CollaborateurComponent } from './collaborateur/collaborateur.component';
import { ObjectifComponent } from './objectif/objectif.component';
//Services
import { ObjectifService } from '../../core/services/objectif.service';
import { EipsComponent } from './eips/eips.component';

import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatTooltipModule } from '@angular/material/tooltip';
import { NiveauListComponent } from './niveau-list/niveau-list.component';







@NgModule({
	declarations: [MyPageComponent, CollaborateurComponent, ObjectifComponent, ManagerComponent, EipsComponent, NiveauListComponent],
	exports: [],
	imports: [
		CommonModule,
		HttpClientModule,
		FormsModule,
		CoreModule,
		PartialsModule,
		MatInputModule,
		MatButtonModule,
		MatSelectModule,
		MatTooltipModule,
		MatPaginatorModule,

	],
	providers: [ObjectifService]
})
export class PagesModule {
}
