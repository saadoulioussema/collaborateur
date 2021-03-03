import { Observable } from 'rxjs';
import { DirectionService } from './../../../services/direction.service';

import { Component, OnInit } from '@angular/core';

import { Direction } from '../../../shared/direction';

@Component({
  selector: 'kt-test',
  templateUrl: './test.component.html',
  styleUrls: ['./test.component.scss']
})
export class TestComponent implements OnInit {
  Directions:Direction[];
  constructor(private directionService : DirectionService) { }

  ngOnInit() {
    this.getAllDirections();
  }
  getAllDirections(){
    this.directionService.getAllDirections().subscribe(data=>{this.Directions=data,console.log("Directions :",this.Directions)});
  }
}
