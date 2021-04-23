// Angular
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
// Components
import { BaseComponent } from './views/theme/base/base.component';
import { ErrorPageComponent } from './views/theme/content/error-page/error-page.component';
import { MyPageComponent } from './views/pages/my-page/my-page.component';
import { CollaborateurComponent } from './views/pages/collaborateur/collaborateur.component';
import { EipsComponent } from './views/pages/eips/eips.component';
import { ManagerComponent } from './views/pages/manager/manager.component';
// Auth
import { AuthGuardService } from './core/services/auth-guard.service';



const routes: Routes = [
	{ path: 'auth', loadChildren: () => import('./views/pages/auth/auth.module').then(m => m.AuthModule) },
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
				path: 'my-page', component: MyPageComponent,
			},
			{
				path: 'eips', component: EipsComponent,
			},
			{
				path: 'evaluate', component: ManagerComponent,
			},
			{
				path: 'autoEvaluate', component: CollaborateurComponent,
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
				path: 'builder',
				loadChildren: () => import('./views/theme/content/builder/builder.module').then(m => m.BuilderModule),
			},
		],
	},
	{
		path: 'error/404',
		component: ErrorPageComponent,
		data: {
			type: 'error-v3',
			code: 404,
			title: 'How did you get here',
			subtitle: 'Sorry we can\'t seem to find the page you\'re looking for',
			desc: 'There may be amisspelling in the URL entered,<br>' + 'or the page you are looking for may no longer exist.',
			image: './assets/media/error/bg6.jpg'
		},
	},
	{
		path: 'error/403',
		component: ErrorPageComponent,
		data: {
			type: 'error-v6',
			code: 404,
			title: 'How did you get here',
			subtitle: 'Sorry we can\'t seem to find the page you\'re looking for',
			desc: 'There may be amisspelling in the URL entered,<br>' + 'or the page you are looking for may no longer exist.',
			image: './assets/media/error/bg5.jpg'
		},
	},
	{ path: 'error/404', component: ErrorPageComponent },
	{ path: 'error/403', component: ErrorPageComponent },
	{ path: ' ', redirectTo: 'dashboard', pathMatch: 'full' },
	{ path: '**', redirectTo: 'error/404', pathMatch: 'full' },

];

@NgModule({
	imports: [
		RouterModule.forRoot(routes),
	],
	exports: [RouterModule],
})
export class AppRoutingModule {
}
