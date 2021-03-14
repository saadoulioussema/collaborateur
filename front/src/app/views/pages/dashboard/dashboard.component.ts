// Angular
import { Component, OnInit } from '@angular/core';

@Component({
	selector: 'kt-dashboard',
	templateUrl: './dashboard.component.html',
	styleUrls: ['dashboard.component.scss'],
})
export class DashboardComponent implements OnInit {

	static flag =false;

	constructor() {
		// let id  = localStorage.getItem("managerId");
		// if (id!=null){
		// 	DashboardComponent.flag=true;
		// }
	}

	ngOnInit() {

	}
}
