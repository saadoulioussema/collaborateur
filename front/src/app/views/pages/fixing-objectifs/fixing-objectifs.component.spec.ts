import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FixingObjectifsComponent } from './fixing-objectifs.component';

describe('FixingObjectifsComponent', () => {
  let component: FixingObjectifsComponent;
  let fixture: ComponentFixture<FixingObjectifsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FixingObjectifsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FixingObjectifsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
