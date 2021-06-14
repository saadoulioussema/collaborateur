import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FixingProjectComponent } from './fixing-project.component';

describe('FixingProjectComponent', () => {
  let component: FixingProjectComponent;
  let fixture: ComponentFixture<FixingProjectComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FixingProjectComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FixingProjectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
