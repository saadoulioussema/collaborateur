import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EvaluationCompetencesComponent } from './evaluation-competences.component';

describe('EvaluationCompetencesComponent', () => {
  let component: EvaluationCompetencesComponent;
  let fixture: ComponentFixture<EvaluationCompetencesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EvaluationCompetencesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EvaluationCompetencesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
