import { TestComponent } from './views/pages/test/test.component';

// Angular
import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
// Components
import {BaseComponent} from './views/theme/base/base.component';
import {ErrorPageComponent} from './views/theme/content/error-page/error-page.component';
// Auth
import {AuthGuardService} from './services/auth-guard.service';

import { CollaborateurComponent } from './views/pages/collaborateur/collaborateur.component';
import { ObjectifComponent } from './views/pages/objectif/objectif.component';

const routes: Routes = [
	{path: 'auth', loadChildren: () => import('./views/pages/auth/auth.module').then(m => m.AuthModule)},
	{
		path: '',
		component: BaseComponent,
		canActivate: [AuthGuardService],
		children: [
			{
				path: 'dashboard',
				loadChildren: () => import('./views/pages/dashboard/dashboard.module').then(m => m.DashboardModule),
			},
			{
				path: 'mail',
				loadChildren: () => import('./views/pages/apps/mail/mail.module').then(m => m.MailModule),
			},
			{
				path: 'ecommerce',
				loadChildren: () => import('./views/pages/apps/e-commerce/e-commerce.module').then(m => m.ECommerceModule),
			},
			{
				path: 'ngbootstrap',
				loadChildren: () => import('./views/pages/ngbootstrap/ngbootstrap.module').then(m => m.NgbootstrapModule),
			},
			{
				path: 'material',
				loadChildren: () => import('./views/pages/material/material.module').then(m => m.MaterialModule),
			},
			{
				path: 'user-management',
				loadChildren: () => import('./views/pages/user-management/user-management.module').then(m => m.UserManagementModule),
			},
			{
				path: 'wizard',
				loadChildren: () => import('./views/pages/wizard/wizard.module').then(m => m.WizardModule),
			},
			{
				path: 'builder',
				loadChildren: () => import('./views/theme/content/builder/builder.module').then(m => m.BuilderModule),
			},
			// {
			// 	path: 'error/403',
			// 	component: ErrorPageComponent,
			// 	data: {
			// 		type: 'error-v6',
			// 		code: 403,
			// 		title: '403... Access forbidden',
			// 		desc: 'Looks like you don\'t have permission to access for requested page.<br> Please, contact administrator',
			// 	},
			// },
			// {path: 'error/:type', component: ErrorPageComponent},
			// {path: '**', redirectTo: 'error/:type', pathMatch: 'full'},
		],
	},
	{path: 'collaborateur', component: CollaborateurComponent},
	{path: 'direction', component: TestComponent},
	{path: 'collaborateur/objectif', component: ObjectifComponent},
	{
		path: 'error/404',
		component: ErrorPageComponent,
		data: {
			type: 'error-v3',
			code: 404,
			title: 'How did you get here',
			subtitle:'Sorry we can\'t seem to find the page you\'re looking for',
			desc: 'There may be amisspelling in the URL entered,<br>' + 'or the page you are looking for may no longer exist.',
			image:'./assets/media/error/bg6.jpg'
		},
	},
	{path: 'error/404', component: ErrorPageComponent},
	{path: ' ', redirectTo: 'dashboard',pathMatch: 'full'},
	{path: '**', redirectTo: 'error/404',pathMatch: 'full'},
	
];

@NgModule({
	imports: [
		RouterModule.forRoot(routes),
	],
	exports: [RouterModule],
})
export class AppRoutingModule {
}
